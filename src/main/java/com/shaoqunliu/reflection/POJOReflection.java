package com.shaoqunliu.reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Plain Ordinary Java Object (POJO) Reflection
 *
 * @author Shaoqun Liu
 * @since 1.8
 */
public class POJOReflection {

    private Class<?> clazz;
    private Object object = null;

    /**
     * Construct object with Class
     *
     * @param clazz Classname.class
     */
    public POJOReflection(Class<?> clazz) {
        this.clazz = clazz;
    }

    /**
     * Construct object with an object
     *
     * @param o the target object
     */
    public POJOReflection(Object o) {
        object = o;
        clazz = object.getClass();
    }

    /**
     * Construct object with the classname that you want to reflected
     *
     * @param className class name
     * @throws ClassNotFoundException if the class was not found
     */
    public POJOReflection(String className) throws ClassNotFoundException {
        clazz = Class.forName(className);
    }

    /**
     * Invoke getter method of the field to get value
     *
     * @param fieldName the field that you want get the value of it
     * @return the object got from the field name
     * @throws IntrospectionException    if an exception occurred during introspection
     * @throws InvocationTargetException if an exception occurred during invoking the getter method
     * @throws IllegalAccessException    if the getter method is inaccessible
     */
    public Object getValue(String fieldName) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, clazz);
        Method method = descriptor.getReadMethod();
        return method.invoke(object);
    }

    /**
     * Invoke the setter method of the field to set a new value to it
     *
     * @param fieldName the field that you want get the value of it
     * @param value     the value you want to set
     * @throws IntrospectionException    if an exception occurred during introspection
     * @throws InvocationTargetException if an exception occurred during invoking the getter method
     * @throws IllegalAccessException    if the getter method is inaccessible
     */
    public void setValue(String fieldName, Object value) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, clazz);
        Method method = descriptor.getWriteMethod();
        method.invoke(object, value);
    }


    /**
     * Performs the given action for each field of the class
     *
     * @param action the action to be performed for each field
     */
    public void forEachField(Consumer<? super Field> action) {
        getFieldStream().forEach(action);
    }

    /**
     * Performs the given action for each specific annotation of each field
     *
     * @param action the action to be performed for each specific annotation of each field
     * @param type   the specific annotation type
     */
    public <T extends Annotation> void forEachAnnotationByFieldByType(
            BiConsumer<? super Field, T> action, Class<T> type) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(action);
        getFieldStream().forEach(field -> {
            for (T annotation : field.getAnnotationsByType(type)) {
                action.accept(field, annotation);
            }
        });
    }

    /**
     * Performs the given action for each specific annotation of the class
     *
     * @param action the action to be performed for each specific annotation of the class
     * @param type   the specific annotation type
     */
    public <T extends Annotation> void forEachAnnotationByType(
            Consumer<T> action, Class<T> type) {
        Objects.requireNonNull(action);
        Objects.requireNonNull(type);
        for (T annotation : clazz.getAnnotationsByType(type)) {
            action.accept(annotation);
        }
    }

    /**
     * Create a stream of field
     *
     * @return the field stream
     */
    public Stream<Field> getFieldStream() {
        return Arrays.stream(clazz.getDeclaredFields());
    }

}
