package finki.ukim.mk.FitnessTrackingApp.controller.exceptionController;

import finki.ukim.mk.FitnessTrackingApp.exceptions.MistakeException;
import finki.ukim.mk.FitnessTrackingApp.exceptions.UsernameNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())
    );
    ex.getBindingResult().getGlobalErrors().forEach(error ->
        errors.put(error.getObjectName(), error.getDefaultMessage())
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler(MistakeException.class)
  public ResponseEntity<String> handleMistakeException(MistakeException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }

  @ExceptionHandler(UsernameNotFound.class)
  public ResponseEntity<String> UsernameNotFound(UsernameNotFound ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String,String>> HttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    Map<String, String> errors = new HashMap<>();
    String errorMessage = ex.getMessage();
    if (errorMessage.contains("Cannot deserialize value of type")) {
      if (errorMessage.contains("Gender")) {
        errors.put("gender", "Invalid Gender type must be either 'Male' or 'Female'");
      }
    }
    else {
      errors.put("gender", "Invalid request format");
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }
}
