package com.shaoqunliu.validation.annotation;

import java.lang.annotation.*;

/**
 * The annotated element will be checked with the database as a like-foreign-key function
 *
 * @author Shaoqun Liu
 * @since 1.8
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DatabaseColumnReference.List.class)
@Documented
public @interface DatabaseColumnReference {

    /**
     * Message will be returned when an invalid value was given
     */
    String message() default "";

    /**
     * Table name
     */
    String table();

    /**
     * Column name
     */
    String column();

    /**
     * Equivalence class
     */
    Class<?>[] groups() default {};

    /**
     * Defines several {@link DatabaseColumnReference} constraints on the same element.
     *
     * @see DatabaseColumnReference
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        DatabaseColumnReference[] value();
    }

}
