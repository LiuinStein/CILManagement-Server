package com.shaoqunliu.reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Plain Ordinary Java Object (POJO) Reflection
 *
 * @author Shaoqun Liu
 * @since 1.8
 */
public class POJOReflection {

    private Class clazz;
    private Object object = null;

    /**
     * Construct object with Class
     *
     * @param clazz Classname.class
     */
    public POJOReflection(Class clazz) {
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

}