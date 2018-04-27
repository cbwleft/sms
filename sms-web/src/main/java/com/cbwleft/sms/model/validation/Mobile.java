package com.cbwleft.sms.model.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;


@Documented
@Constraint(validatedBy = { })
@Pattern(regexp = "[1][0-9]{10}")
@Target({ FIELD })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface Mobile {
	
	String message() default "{com.cbwleft.sms.constraints.Mobile.messsage}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
