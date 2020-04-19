package com.vasylbhd.randommemeapi.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxPageCountValidator implements ConstraintValidator<Max, Integer> {
   private long max;
   public void initialize(Max constraint) {
      this.max = constraint.value();
   }

   public boolean isValid(Integer num, ConstraintValidatorContext context) {
      if (num == null) {
         return true;
      }
      return num <= max;
   }
}
