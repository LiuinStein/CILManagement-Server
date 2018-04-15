package com.shaoqunliu.validation.validator;

import com.shaoqunliu.validation.exception.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class HibernateValidator extends AbstractValidator {

    /**
     * The Hibernate validator
     */
    private Validator validator = Validation.byProvider(org.hibernate.validator.HibernateValidator.class)
            .configure().failFast(isFailFast())
            .buildValidatorFactory().getValidator();

    /**
     * Whether the validator validate the default @annotation
     */
    private boolean passDefault = false;

    /**
     * @see com.shaoqunliu.validation.ValidationAdapter#validate(Object, Class[])
     */
    @Override
    public <T> T validate(T object, Class<?>... groups) throws ValidationException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, groups);
        if (isPassDefault() && !(isFailFast() && constraintViolations.size() > 0)) {
            constraintViolations.addAll(validator.validate(object));
        }
        if (constraintViolations.size() > 0) {
            StringBuilder message = new StringBuilder(64);
            constraintViolations.forEach(violation ->
                    message.append(violation.getPropertyPath().toString()).append(" ")
                            .append(violation.getMessage()).append("; ")
            );
            throw new ValidationException(message.toString());
        }
        return object;
    }

    public boolean isPassDefault() {
        return passDefault;
    }

    public void setPassDefault(boolean passDefault) {
        this.passDefault = passDefault;
    }
}
