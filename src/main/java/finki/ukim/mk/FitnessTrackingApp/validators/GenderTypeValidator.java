package finki.ukim.mk.FitnessTrackingApp.validators;

import finki.ukim.mk.FitnessTrackingApp.validators.impl.GenderTypeValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
@Constraint(validatedBy = GenderTypeValidatorImpl.class)
public @interface GenderTypeValidator {
  String message() default "Invalid examination status";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
