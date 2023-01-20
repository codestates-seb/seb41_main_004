package com.codestates.azitserver.global.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {
	private EnumValue annotation;

	@Override
	public void initialize(EnumValue constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		Object[] enumValues = this.annotation.enumClass().getEnumConstants();
		if (enumValues == null) {
			return false;
		}

		for (Object enumValue : enumValues) {
			if (value.equals(enumValue.toString())
				|| (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
				return true;
			}
		}

		return false;
	}
}