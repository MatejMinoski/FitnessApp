package finki.ukim.mk.FitnessTrackingApp.service;

import finki.ukim.mk.FitnessTrackingApp.models.dto.UpsertUserDto;
import finki.ukim.mk.FitnessTrackingApp.models.dto.UsersDto;
import finki.ukim.mk.FitnessTrackingApp.models.dto.VerifyUserDto;

public interface UserService {
  UsersDto registerDoctor(UpsertUserDto userDto);

  UsersDto loginDoctor(UpsertUserDto userDto);

  void verifyUser(VerifyUserDto verifyUserDto);

  void resendVerificationEmail(String username);
}
