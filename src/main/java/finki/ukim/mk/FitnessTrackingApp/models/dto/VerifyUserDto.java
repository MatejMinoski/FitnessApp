package finki.ukim.mk.FitnessTrackingApp.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyUserDto {

private String verifyToken;
  @NotBlank(message = "Username is required")
  @Size(min = 5, message = "Username must be at least 5 characters")
private String username;
}
