package com.coding.assignment.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {


    String message() default "Invalid format, valid formats is 959xxxxxxxxx";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}