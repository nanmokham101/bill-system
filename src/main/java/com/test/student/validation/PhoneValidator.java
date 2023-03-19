package com.test.student.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone paramA) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext ctx) {
        if(phoneNo == null) {
            return false;
        }
        if (phoneNo.matches("\\d{11}")) return true;
        else return false;
    }



}