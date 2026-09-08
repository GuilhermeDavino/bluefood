package com.blue.bluefood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {
	
	private int numeroMultiplo;
	
	@Override
	public void initialize(Multiplo constraintAnnotation) {
		numeroMultiplo = constraintAnnotation.numero();
	}
	
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		var isValid = true;
		
		if(value != null) {
			var valorDecimal = BigDecimal.valueOf(value.doubleValue());
			var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);
			var resto = valorDecimal.remainder(multiploDecimal);
			
			isValid = BigDecimal.ZERO.compareTo(resto) == 0;
		} else {
			isValid = false;
		}
		
		return isValid;
	}

}
