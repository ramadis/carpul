package ar.edu.itba.paw.webapp.forms.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class TripTimeValidator implements ConstraintValidator<TripTimeAnnotation, Object> {

  private String field;
  private String fieldMatch;

  public void initialize(TripTimeAnnotation constraintAnnotation) {
      this.field = constraintAnnotation.departure();
      this.fieldMatch = constraintAnnotation.arrival();
  }

  public boolean isValid(Object value, ConstraintValidatorContext context) {

      Long departure = (Long) new BeanWrapperImpl(value).getPropertyValue(field);
      Long arrival = (Long) new BeanWrapperImpl(value).getPropertyValue(fieldMatch);
       
      if (departure != null) {
          return departure < arrival;
      } else {
          return arrival == null;
      }
  }
}