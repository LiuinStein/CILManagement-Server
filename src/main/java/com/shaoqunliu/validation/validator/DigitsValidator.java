package com.shaoqunliu.validation.validator;

import com.shaoqunliu.reflection.AnnotationReflection;
import com.shaoqunliu.validation.annotation.DigitsPattern;

import java.lang.reflect.InvocationTargetException;


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
        classFunctionMap.put(DigitsPattern.class, (x, y) -> {
            if (!(x instanceof Number)) {
                return "invalid type, must be a number";
            }
            try {
                AnnotationReflection reflection = new AnnotationReflection(y);
                if (x.toString().matches(reflection.getMember("regexp").toString())) {
                    return "";
                }
                String message = reflection.getMember("message").toString();
                return message.length() == 0 ? "invalid number " + x + " was given" : message;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return "invalid number " + x + " was given";
        });
    }
}