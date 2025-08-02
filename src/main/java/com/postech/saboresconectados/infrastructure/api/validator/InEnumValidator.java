package com.postech.saboresconectados.infrastructure.api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InEnumValidator implements ConstraintValidator<InEnum, String> {

    private List<String> acceptedValues;

    private String message;

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(InEnum annotation) {
        this.message = annotation.message();
        this.enumClass = annotation.enumClass();
        this.acceptedValues = Stream
                .of(enumClass.getEnumConstants())
                .map(e -> {
                    try {
                        Method getValueMethod = this.enumClass.getMethod("getValue");
                        return (String) getValueMethod.invoke(e);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException exception) {
                        throw new IllegalStateException(
                                "Error trying to invoke getValue() method on enum" + e.getClass().getName(),
                                exception);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean isValid = acceptedValues.contains(value);
        if (!isValid) {
            String finalMessage = this.message
                    .replace("{values}", String.join(", ", this.acceptedValues));
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(finalMessage).addConstraintViolation();
            return false;
        }
        return true;
    }
}
