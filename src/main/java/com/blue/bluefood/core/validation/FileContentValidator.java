package com.blue.bluefood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentValidator implements ConstraintValidator<FileContentType, MultipartFile> {
	
	private String[] tiposSuportados;
	
	@Override
	public void initialize(FileContentType constraintAnnotation) {
		
		this.tiposSuportados = constraintAnnotation.allowed();
	}
	
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		
		
		boolean isValid = false;
		
		if (value == null) {
			return isValid;
		}
		
		for (String tipo : tiposSuportados) {
			if (tipo.equals(value.getContentType())) {
				isValid = true;
				break;
			}
		}
		
		return isValid;
		
	}



}
