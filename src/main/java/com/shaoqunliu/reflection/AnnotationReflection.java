package com.shaoqunliu.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Reflect a runtime annotation
 *
 * @author Shaoqun Liu
 * @since 1.8
 */
public class AnnotationReflection {

    private final Map<String, Object> members;

    public <T extends Annotation> AnnotationReflection(T annotation) throws InvocationTargetException {
        members = new HashMap<>();
        for (Method method : annotation.annotationType().getDeclaredMethods()) {
            try {
                members.put(method.getName(), method.invoke(annotation));
            } catch (IllegalAccessException ignored) {
                // will never happened at an annotation interface
            }
        }
    }

    /**
     * Get annotation members & its value
     *
     * @param name member name
     * @return the value of this member
     */
    public Object getMember(String name) {
        return members.get(name);
    }

}

