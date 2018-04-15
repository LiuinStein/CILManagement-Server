package com.shaoqunliu.validation;

import com.shaoqunliu.validation.exception.ValidationInternalException;

/**
 * assert
 *
 * @author Shaoqun Liu
 */
public class Contracts {

    public static void assertNotEmpty(String test, String message) throws ValidationInternalException {
        if (test == null || test.length() == 0) {
            throw new ValidationInternalException(message);
        }
    }

    public static void assertNotNull(Object object, String message) throws ValidationInternalException {
        if (object == null) {
            throw new ValidationInternalException(message);
        }
    }

    public static void assertMatchPattern(String test, String pattern, String message) throws ValidationInternalException {
        assertNotEmpty(test, message);
        if (!test.matches(pattern)) {
            throw new ValidationInternalException(message);
        }
    }

}
