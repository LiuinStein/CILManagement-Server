package com.shaoqunliu.validation;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidationUtils {
    private static Validator validator =
            Validation.byProvider(HibernateValidator.class)
                    .configure().failFast(true)
                    .buildValidatorFactory().getValidator();

    public static <T> T validate(T obj) throws ValidationException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        if (constraintViolations.size() > 0) {
            StringBuilder message = new StringBuilder("Invalid Data List: ");
            for (ConstraintViolation violation : constraintViolations) {
                message.append(violation.getMessage()).append("; ");
            }
            throw new ValidationException(message.toString());
        }
        return obj;
    }
}
