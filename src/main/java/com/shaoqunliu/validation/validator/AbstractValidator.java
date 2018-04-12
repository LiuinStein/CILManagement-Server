package com.shaoqunliu.validation.validator;

import com.shaoqunliu.reflection.AnnotationReflection;
import com.shaoqunliu.reflection.POJOReflection;
import com.shaoqunliu.validation.AbstractValidation;
import com.shaoqunliu.validation.exception.ValidationException;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiFunction;

/**
 * @author Shaoqun Liu
 * @since 1.8
 */
public abstract class AbstractValidator extends AbstractValidation {
    protected static HashMap<Class<? extends Annotation>, BiFunction<Object, Annotation, String>> classFunctionMap = new HashMap<>();

    /**
     * @see com.shaoqunliu.validation.ValidationAdapter#validate(Object, Class[])
     */
    @Override
    public <T> T validate(T object, Class<?>... groups) throws ValidationException {
        POJOReflection reflection = new POJOReflection(object);
        StringBuilder message = new StringBuilder(128);
        classFunctionMap.forEach((type, function) -> {
            if (isFailFast() && message.length() != 0) {
                return;
            }

            reflection.forEachAnnotationByFieldByType((field, annotation) -> {
                try {
                    AnnotationReflection annotationReflection = new AnnotationReflection(annotation);
                    Class<?>[] classes = (Class<?>[]) annotationReflection.getMember("groups");
                    if ((groups.length == 0 && classes.length == 0) ||
                            Arrays.stream(groups).anyMatch(Arrays.asList(classes)::contains)) {
                        message.append(function.apply(reflection.getValue(field.getName()), annotation));
                    }
                } catch (Exception ignored) {
                    // ignored
                }
            }, type);

        });
        if (message.length() != 0) {
            throw new ValidationException(message.toString());
        }
        return object;
    }

}
