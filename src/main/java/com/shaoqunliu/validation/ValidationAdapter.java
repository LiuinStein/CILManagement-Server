package com.shaoqunliu.validation;

import com.shaoqunliu.validation.exception.ValidationException;

/**
 * Validation adapter interface
 * <p>
 * A practice of Adapter design pattern.
 * </p>
 * @author Shaoqun Liu
 */
public interface ValidationAdapter {
    /**
     * Validates all constraints on object
     *
     * @param object the object to validate
     * @param groups the group or list of groups targeted for validation
     * @return the input object if nothing was invalidated
     * @throws ValidationException when constraint validated
     */
    <T> T validate(T object, Class... groups) throws ValidationException;
}
