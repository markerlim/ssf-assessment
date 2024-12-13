package vttp.batch5.ssf.noticeboard.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListValidator.class)
public @interface ValidList {
    String message() default "You must pick at least one!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
