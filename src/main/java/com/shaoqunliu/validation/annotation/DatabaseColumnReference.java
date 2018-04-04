package com.shaoqunliu.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseColumnReference {

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
     * The default value {} means not to check anything on the database
     */
    Class<?>[] groups() default {};

}
