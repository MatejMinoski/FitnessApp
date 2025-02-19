package finki.ukim.mk.FitnessTrackingApp.controller;

import finki.ukim.mk.FitnessTrackingApp.models.dto.UpsertUserDto;
import finki.ukim.mk.FitnessTrackingApp.models.dto.UsersDto;
import finki.ukim.mk.FitnessTrackingApp.models.dto.VerifyUserDto;
import finki.ukim.mk.FitnessTrackingApp.models.response.LoginResponse;
import finki.ukim.mk.FitnessTrackingApp.service.UserService;
import finki.ukim.mk.FitnessTrackingApp.service.impl.JWTService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
  private final UserService userService;
  private final JWTService jwtService;

  @PostMapping("/register")
  public ResponseEntity<UsersDto> register(@RequestBody @Valid UpsertUserDto upsertUserDto) {
    return ResponseEntity.ok(userService.registerDoctor(upsertUserDto));
  }

  @PostMapping("/verifyUser")
  public ResponseEntity<?> verifyUser(@RequestBody @Valid VerifyUserDto verifyUserDto) {
    userService.verifyUser(verifyUserDto);
    return ResponseEntity.ok("Account verified successfully");
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody UpsertUserDto usersDto) {
    UsersDto usersDto1 = userService.loginDoctor(usersDto);
    String token = jwtService.generateToken(usersDto1.getUsername());
    LoginResponse loginResponse = new LoginResponse(token,
        usersDto1.getFirstName(),
        usersDto1.getRole());
    return ResponseEntity.ok(loginResponse);
  }

  @PostMapping("/resend")
  public ResponseEntity<?> resend(@RequestBody @Valid VerifyUserDto verifyUserDto) {
    userService.resendVerificationEmail(verifyUserDto.getUsername());
    return ResponseEntity.ok("The verification code has been resent");
  }
}
