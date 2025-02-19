package finki.ukim.mk.FitnessTrackingApp.validators.impl;

import finki.ukim.mk.FitnessTrackingApp.models.enums.Gender;
import finki.ukim.mk.FitnessTrackingApp.validators.GenderTypeValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderTypeValidatorImpl implements ConstraintValidator<GenderTypeValidator, Gender> {
  @Override
  public boolean isValid(Gender gender, ConstraintValidatorContext constraintValidatorContext) {
    if (gender == null) {
      return false;
    }
    return gender == Gender.Male || gender == Gender.Female;
  }
}
