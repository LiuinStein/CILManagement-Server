package com.shaoqunliu.validation.annotation;

import java.lang.annotation.*;

/**
 * Validate digits as a String with Regex expression
 *
 * @author Shaoqun Liu
 * @since 1.8
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DigitsPattern.List.class)
@Documented
public @interface DigitsPattern {

    /**
     * Message will be returned when an invalid value was given
     */
    String message() default "";

    /**
     * the regular expression to match
     */
    String regexp();

    /**
     * the groups the constraint belongs to
     */
    Class<?>[] groups() default { };

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {

        DigitsPattern[] value();
    }

}
