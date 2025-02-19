package finki.ukim.mk.FitnessTrackingApp.models.response;

import finki.ukim.mk.FitnessTrackingApp.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
  private String token;
  private String name;
  private Role role;
}
