package finki.ukim.mk.FitnessTrackingApp.models.dto;

import finki.ukim.mk.FitnessTrackingApp.models.enums.Gender;
import finki.ukim.mk.FitnessTrackingApp.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
  private Long id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private Integer age;
  private Gender gender;
  private Integer height;
  private Integer weight;
  private Role role;
}
