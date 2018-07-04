package com.cbwleft.sms.model.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;


@Documented
@Constraint(validatedBy = { })
@NotBlank
@Length(max = 100)
@Target({ FIELD })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface Content {
	
	String message() default "{com.cbwleft.sms.constraints.Content.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
