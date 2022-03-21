package com.hilton.assignment.annotation;

import com.hilton.assignment.validator.IpValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Copyright Any portion of this assignment's code are not allowed to use in business or production.
 *
 * Custom annotation for ip validator
 *
 * @author Anjana Yadav
 */
@Documented
@Constraint(validatedBy = IpValidator.class)
@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidIP {
    String message() default "Invalid IP Address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}