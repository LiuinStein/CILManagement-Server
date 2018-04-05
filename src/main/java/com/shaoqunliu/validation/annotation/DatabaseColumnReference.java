package com.shaoqunliu.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseColumnReference {

    /**
     * Message will be returned when an invalid value was given
     */
    String message() default "";

    /**
     * Table name
     */
    String table() default "";

    /**
     * Column name
     */
    String column() default "";

    /**
     * Equivalence class
     */
    Class<?>[] groups() default {};

}
