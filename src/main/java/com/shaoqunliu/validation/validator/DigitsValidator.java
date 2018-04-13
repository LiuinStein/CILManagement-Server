package com.shaoqunliu.validation.validator;

import com.shaoqunliu.validation.annotation.DigitsPattern;

/**
 * Digits validator
 *
 * @author Shaoqun Liu
 * @since 1.8
 */
public class DigitsValidator extends AbstractValidator {

    static {
        /*
         * DigitsPattern validation function
         */
        classFunctionMap.put(DigitsPattern.class, (obj, function) -> {
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
            return obj.toString().matches(function.apply("regexp").toString());
        });
    }
}