package com.blue.bluefood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValorZeroIncluiDecscricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {
	private String valorField;
	private String descricaoField;
	private String descricaoObrigatoria;
	
	@Override
	public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
		valorField = constraintAnnotation.valorField();
		descricaoField = constraintAnnotation.descricaoField();
		descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
		
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		var isValid = true;
		
		try {
			BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(value.getClass(), valorField)
					.getReadMethod().invoke(value);
			
			String descricao = (String) BeanUtils.getPropertyDescriptor(value.getClass(), descricaoField)
					.getReadMethod().invoke(value);
			
			if(valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
				isValid = descricao.toLowerCase().contains(descricaoObrigatoria.toLowerCase());
				return isValid;
			}
		} catch (Exception exception) {
			throw new ValidationException(exception);
		}
		return isValid;
	}

}
