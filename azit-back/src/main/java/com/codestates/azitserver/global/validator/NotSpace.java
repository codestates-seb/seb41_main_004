package com.codestates.azitserver.global.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotSpaceValidator.class})
public @interface NotSpace {
	String message() default "must not be empty space.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
