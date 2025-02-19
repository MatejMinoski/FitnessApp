package finki.ukim.mk.FitnessTrackingApp.service.impl;

import finki.ukim.mk.FitnessTrackingApp.exceptions.MistakeException;
import finki.ukim.mk.FitnessTrackingApp.exceptions.UsernameNotFound;
import finki.ukim.mk.FitnessTrackingApp.mapper.UsersMapper;
import finki.ukim.mk.FitnessTrackingApp.models.Users;
import finki.ukim.mk.FitnessTrackingApp.models.dto.UpsertUserDto;
import finki.ukim.mk.FitnessTrackingApp.models.dto.UsersDto;
import finki.ukim.mk.FitnessTrackingApp.models.dto.VerifyUserDto;
import finki.ukim.mk.FitnessTrackingApp.repository.UserRepository;
import finki.ukim.mk.FitnessTrackingApp.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final JWTService jwtService;
  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;
  private final UsersMapper usersMapper;
  private final AuthenticationManager authenticationManager;

  @Override
  public UsersDto registerDoctor(UpsertUserDto userDto) {
    if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
      throw new MistakeException("Username already exists");
    }
    if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
      throw new MistakeException("Email already exists");
    }
    Users users = usersMapper.toUsers(userDto);
    users.setPassword(passwordEncoder.encode(userDto.getPassword()));
    users.setVerificationToken(generateVerificationCode());
    users.setVerificationTokenExpires(LocalDateTime.now().plusMinutes(15));
    users.setEnabled(false);
    sendVerificationEmail(users);

    return usersMapper.toUsersDto(userRepository.save(users));
  }

  @Override
  public UsersDto loginDoctor(UpsertUserDto userDto) {
    Users users = userRepository.findByUsername(userDto.getUsername()).orElseThrow(() ->
        new UsernameNotFound("Invalid username or password"));
    if (!users.isEnabled()) {
      throw new MistakeException("Account not verified please verify your email ");
    }
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword())
      );
    } catch (BadCredentialsException e) {
      throw new UsernameNotFound("Invalid username or password");
    }
    return usersMapper.toUsersDto(users);
  }

  @Override
  public void verifyUser(VerifyUserDto verifyUserDto) {
    Optional<Users> usersOptional = userRepository.findByUsername(verifyUserDto.getUsername());
    if (usersOptional.isPresent()) {
      Users users = usersOptional.get();
      if (users.isEnabled()) {
        throw new MistakeException("Account already verified");
      }
      if (users.getVerificationToken().equals(verifyUserDto.getVerifyToken())) {
        users.setEnabled(true);
        users.setVerificationToken(null);
        users.setVerificationTokenExpires(null);
        userRepository.save(users);
      } else {
        throw new MistakeException("Invalid verification token");
      }
    } else {
      throw new UsernameNotFound("Username not found");
    }

  }

  @Override
  public void resendVerificationEmail(String username) {
    Optional<Users> usersOptional = userRepository.findByUsername(username);
    if (usersOptional.isPresent()) {
      Users users = usersOptional.get();
      if (users.isEnabled()) {
        throw new MistakeException("Account already verified");
      }
      users.setVerificationToken(generateVerificationCode());
      users.setVerificationTokenExpires(LocalDateTime.now().plusMinutes(15));
      sendVerificationEmail(users);
      userRepository.save(users);
    }
  }

  public void sendVerificationEmail(Users users) {
    String subject = "Account Verification";
    String verificationCode = "VERIFICATION CODE " + users.getVerificationToken();
    String htmlMessage = "<html>"
        + "<body style=\"font-family: Arial, sans-serif;\">"
        + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
        + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
        + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
        + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
        + "<h3 style=\"color: #333;\">Verification Code:</h3>"
        + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
        + "</div>"
        + "</div>"
        + "</body>"
        + "</html>";
    try {
      emailService.sendVerificationMail(users.getEmail(), subject, htmlMessage);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  public String generateVerificationCode() {
    Random random = new Random();
    int code = random.nextInt(900000) + 100000;
    return String.valueOf(code);
  }
}
