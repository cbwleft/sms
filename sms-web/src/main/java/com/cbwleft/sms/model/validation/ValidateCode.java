package com.cbwleft.sms.model.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


@Documented
@Constraint(validatedBy = { })
@NotNull
@Length(min = 4, max = 6)
@Target({ FIELD })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface ValidateCode {
	
	String message() default "{com.cbwleft.sms.constraints.ValidateCode.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
