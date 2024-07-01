package fr.carrefour.kata.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.carrefour.kata.app.data.entity.Customer;
import fr.carrefour.kata.app.data.entity.Token;
import fr.carrefour.kata.app.data.repository.CustomerRepository;
import fr.carrefour.kata.app.data.repository.TokenRepository;
import fr.carrefour.kata.security.model.AuthenticationRequest;
import fr.carrefour.kata.security.model.AuthenticationResponse;
import fr.carrefour.kata.security.model.RegisterRequest;
import fr.carrefour.kata.security.model.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final CustomerRepository customerRepository;

  public AuthenticationResponse register(RegisterRequest request) {
    log.info("Register a new user");
    Customer user = buildCustomer(request);
    Customer savedUser = customerRepository.save(user);
    String jwtToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);
    log.info("User registered");

    return buildResponse(jwtToken, refreshToken);
  }

  private Customer buildCustomer(RegisterRequest request) {
    return Customer.builder()
            .lastname(request.getLastname())
            .firstname(request.getFirstname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    log.info("Authenticate a user");
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    Customer user = customerRepository.findByEmail(request.getEmail()).orElseThrow();
    final String jwtToken = jwtService.generateToken(user);
    final String refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    log.info("User authenticated");

    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  private void saveUserToken(Customer user, String jwtToken) {
    Token token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(Customer user) {
    List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getCustomerId());
    if (validUserTokens.isEmpty()) return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }

    final String refreshToken = authHeader.substring(7);
    final String username = jwtService.extractUsername(refreshToken);
    if (username != null) {
      Customer user = this.customerRepository.findByEmail(username).orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        final String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        AuthenticationResponse authResponse = buildResponse(accessToken, refreshToken);
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }

  private AuthenticationResponse buildResponse(String accessToken, String refreshToken) {
    return AuthenticationResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }


}
