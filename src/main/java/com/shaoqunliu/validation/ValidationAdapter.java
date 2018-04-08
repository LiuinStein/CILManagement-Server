package com.shaoqunliu.validation;

import com.shaoqunliu.validation.exception.ValidationException;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Validation adapter interface
 * <p>
 * A practice of Adapter design pattern.
 * </p>
 *
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
    <T> T validate(T object, Class<?>... groups) throws ValidationException;

    /**
     * Functional object validation
     *
     * @param object    the object need validation
     * @param predicate the functor
     * @return true if the input argument matches the predicate, otherwise false
     * @throws ValidationException when the given array contains invalid object
     */
    default <T> T validate(T object, Predicate<? super T> predicate) throws ValidationException {
        if (predicate.test(object)) {
            return object;
        }
        throw new ValidationException("invalid value " + object.toString());
    }

    /**
     * Functional array validation
     *
     * @param objects the array need to validate
     * @param predicate the functor
     * @return true if all of the element in the array matches the predicate, otherwise false
     * @throws ValidationException when the given array contains invalid object
     */
    default <T> T[] validate(T[] objects, Predicate<? super T> predicate) throws ValidationException {
        if (Arrays.stream(objects).allMatch(predicate)) {
            return objects;
        }
        throw new ValidationException("invalid array");
    }
}
