package finki.ukim.mk.FitnessTrackingApp.models.dto;

import finki.ukim.mk.FitnessTrackingApp.models.enums.Gender;
import finki.ukim.mk.FitnessTrackingApp.validators.GenderTypeValidator;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpsertUserDto {
  @NotBlank(message = "First Name is required")
    private String firstName;
  @NotBlank(message = "Last Name is required")
  private String lastName;
  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  private String email;
  @NotBlank(message = "Password is required")
  @Pattern( regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+{}:;<>,.?/~`-]).{6,}$",
      message = "Password must be at least 6 characters long, contain an uppercase letter, " +
          "a digit, and a special character.")
  private String password;
  @NotBlank(message = "Username is required")
  @Size(min = 5,message = "Username must be at least 5 characters long")
  private String username;
  @GenderTypeValidator(message = "Gender must be either Male or Female entered")
  private Gender gender;
  @NotNull(message = "Age is required")
  private Integer age;
  @NotNull(message = "Height is required")
  private Integer height;
  @NotNull(message = "Weight is required")
  private Integer weight;
}
