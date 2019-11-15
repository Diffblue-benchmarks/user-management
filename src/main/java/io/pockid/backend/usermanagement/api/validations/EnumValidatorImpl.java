package io.pockid.backend.usermanagement.api.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    private List<String> valueList = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return valueList.contains(value.toUpperCase());
    }

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        valueList = new ArrayList<>();
        var enumClass = constraintAnnotation.enumClazz();
        @SuppressWarnings("rawtypes")
        var enumValArr = enumClass.getEnumConstants();
        for (@SuppressWarnings("rawtypes")
                var enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }
}
