package vttp.batch5.ssf.noticeboard.validation;

import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ListValidator implements ConstraintValidator<ValidList, List<String>> {

    @Override
    public void initialize(ValidList constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if(!value.isEmpty()){
            return true;
        }
        return false;
    }
}

