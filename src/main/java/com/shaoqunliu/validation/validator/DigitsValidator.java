package com.shaoqunliu.validation.validator;

import com.shaoqunliu.validation.annotation.DigitsPattern;

import java.util.function.Function;

/**
 * Digits validator
 *
 * @author Shaoqun Liu
 * @since 1.8
 */
public class DigitsValidator extends AbstractValidator {

    public DigitsValidator() {
        classFunctionMap.put(DigitsPattern.class, this::digitsPattern);
    }

    /**
     * DigitsPattern validation function
     */
    private Boolean digitsPattern(Object obj, Function<String, Object> getMember) {
        if (obj == null) {
            /*
             * null is considered to be valid
             */
            return true;
        }
        if (!(obj instanceof Number)) {
            /*
             * no need for check the java primitive types
             * because, the java primitive type will be autoboxed into an Object
             */
            return false;
        }
        return obj.toString().matches(getMember.apply("regexp").toString());
    }
}