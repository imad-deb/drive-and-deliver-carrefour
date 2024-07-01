package fr.carrefour.kata.security.model;

import fr.carrefour.kata.app.data.dto.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String lastname;
  private String firstname;
  private String email;
  private String password;
  private RoleEnum role;
}
