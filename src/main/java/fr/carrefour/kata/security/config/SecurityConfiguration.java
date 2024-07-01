package fr.carrefour.kata.security.config;

import fr.carrefour.kata.core.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static fr.carrefour.kata.app.data.dto.PermissionEnum.*;
import static fr.carrefour.kata.app.data.dto.RoleEnum.ADMIN;
import static fr.carrefour.kata.app.data.dto.RoleEnum.USER;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {Constants.AUTH_ENDPOINT+"/**",
            "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**",
            "/configuration/ui", "/configuration/security",
            "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources", "/swagger-resources/**",
            "/webjars/**"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers(Constants.CUSTOMER_ENDPOINT+"/**").hasAnyRole(ADMIN.name(), USER.name())
                                .requestMatchers(GET, Constants.CUSTOMER_ENDPOINT+"/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                .requestMatchers(POST, Constants.CUSTOMER_ENDPOINT+"/**").hasAnyAuthority(ADMIN_CREATE.name(), USER_CREATE.name())
                                .requestMatchers(PUT, Constants.CUSTOMER_ENDPOINT+"/**").hasAnyAuthority(ADMIN_UPDATE.name(), USER_UPDATE.name())
                                .requestMatchers(DELETE,Constants.CUSTOMER_ENDPOINT+"/**").hasAnyAuthority(ADMIN_DELETE.name(), USER_DELETE.name())
                                .requestMatchers(Constants.DELIVERY_ENDPOINT+"/**").hasAnyRole(ADMIN.name(), USER.name())
                                .requestMatchers(GET, Constants.DELIVERY_ENDPOINT+"/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                .requestMatchers(POST, Constants.DELIVERY_ENDPOINT+"/**").hasAnyAuthority(ADMIN_CREATE.name(), USER_CREATE.name())
                                .requestMatchers(PUT, Constants.DELIVERY_ENDPOINT+"/**").hasAnyAuthority(ADMIN_UPDATE.name(), USER_UPDATE.name())
                                .requestMatchers(DELETE, Constants.DELIVERY_ENDPOINT+"/**").hasAnyAuthority(ADMIN_DELETE.name(), USER_DELETE.name())
                                .requestMatchers(Constants.KAFKA_ENDPOINT+"/**").hasAnyRole(ADMIN.name(), USER.name())
                                .requestMatchers(GET, Constants.KAFKA_ENDPOINT+"/**").hasAnyAuthority(ADMIN_READ.name(), USER_READ.name())
                                .requestMatchers(POST, Constants.KAFKA_ENDPOINT+"/**").hasAnyAuthority(ADMIN_CREATE.name(), USER_CREATE.name())
                                .requestMatchers(PUT, Constants.KAFKA_ENDPOINT+"/**").hasAnyAuthority(ADMIN_UPDATE.name(), USER_UPDATE.name())
                                .requestMatchers(DELETE, Constants.KAFKA_ENDPOINT+"/**").hasAnyAuthority(ADMIN_DELETE.name(), USER_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl(Constants.AUTH_ENDPOINT+"/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }
}
