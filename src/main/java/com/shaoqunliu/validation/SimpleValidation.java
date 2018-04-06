package com.shaoqunliu.validation;

import com.shaoqunliu.validation.exception.ValidationException;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @author Shaoqun Liu
 */
public class SimpleValidation extends AbstractValidation {

    /**
     * The Hibernate validator
     */
    private Validator validator = Validation.byProvider(HibernateValidator.class)
            .configure().failFast(isFailFast())
            .buildValidatorFactory().getValidator();

    /**
     * Whether the validator validate the default @annotation
     */
    private boolean passDefault = false;

    /**
     * @see com.shaoqunliu.validation.ValidationAdapter
     */
    @Override
    public <T> T validate(T object, Class... groups) throws ValidationException {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, groups);
        if (isPassDefault() && !(isFailFast() && constraintViolations.size() > 0) && groups.length > 0) {
            constraintViolations.addAll(validator.validate(object));
        }
        if (constraintViolations.size() > 0) {
            StringBuilder message = new StringBuilder(64);
            for (ConstraintViolation violation : constraintViolations) {
                message.append(violation.getPropertyPath().toString()).append(" ")
                        .append(violation.getMessage()).append("; ");
            }
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
