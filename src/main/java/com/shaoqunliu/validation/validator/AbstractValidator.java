package com.shaoqunliu.validation.validator;

import com.shaoqunliu.reflection.AnnotationReflection;
import com.shaoqunliu.reflection.POJOReflection;
import com.shaoqunliu.validation.AbstractValidation;
import com.shaoqunliu.validation.exception.ValidationException;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Shaoqun Liu
 * @since 1.8
 */
public abstract class AbstractValidator extends AbstractValidation {
    /**
     * Annotation-ValidationFunction Map
     * <pre>HashMap< annotation, annotationHandler ></pre>
     * <p>
     * About the annotation handler:
     * <pre>BiFunction< Object, Function< String, Object>, Boolean></pre>
     * There are two input parameters:
     * the first Object is the object that need to be checked
     * the second <pre>Function< String, Object></pre>
     * @see com.shaoqunliu.reflection.AnnotationReflection#getMember(String)
     * return true if value is valid, otherwise false
     * </p>
     */
    protected final HashMap<Class<? extends Annotation>, BiFunction<Object, Function<String, Object>, Boolean>> classFunctionMap = new HashMap<>();

    /**
     * @see com.shaoqunliu.validation.ValidationAdapter#validate(Object, Class[])
     */
    @Override
    public <T> T validate(T object, Class<?>... groups) throws ValidationException {
        sanityCheckGroups(groups);
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
                            Arrays.stream(classes).anyMatch(Arrays.asList(groups)::contains)) {
                        Object value = reflection.getValue(field.getName());
                        if (!function.apply(value, annotationReflection::getMember)) {
                            String annotationMessage = annotationReflection.getMember("message").toString();
                            message.append(annotationMessage.length() == 0 ? "invalid value " + value.toString() + " was given" : annotationMessage);
                        }
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
