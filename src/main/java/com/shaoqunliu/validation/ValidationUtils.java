package com.shaoqunliu.validation;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidationUtils {
    /**
     * The validation service provider
     *
     * When fail fast is enabled the validation will stop on the first constraint violation detected.
     * If you change the fail fast option, it will be take effect after you restart the tomcat container.
     */
    private static Validator validator =
            Validation.byProvider(HibernateValidator.class)
                    .configure().failFast(false)
                    .buildValidatorFactory().getValidator();

    /**
     * Validates all constraints on object
     *
     * @param object the object to validate
     * @param groups the group or list of groups targeted for validation
     * @return the input object if nothing was invalidated
     * @throws ValidationException when constraint validated
     */
    public static <T> T validate(T object, Class... groups) throws ValidationException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, groups);
        if (groups.length > 0) {
            constraintViolations.addAll(validator.validate(object));
        }
        if (constraintViolations.size() > 0) {
            StringBuilder message = new StringBuilder();
            for (ConstraintViolation violation : constraintViolations) {
                message.append(violation.getPropertyPath().toString()).append(" ")
                        .append(violation.getMessage()).append("; ");
            }
            throw new ValidationException(message.toString());
        }
        return object;
    }
}
