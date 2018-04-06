package com.shaoqunliu.reflection;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class POJOReflection {

    private Class clazz;
    private Object object = null;

    public POJOReflection(Class clazz) {
        this.clazz = clazz;
    }

    public POJOReflection(Object o) {
        object = o;
        clazz = object.getClass();
    }

    public POJOReflection(String className) throws ClassNotFoundException {
        clazz = Class.forName(className);
    }

    public Object getValue(String fieldName) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, clazz);
        Method method = descriptor.getReadMethod();
        return method.invoke(object);
    }

    public void setValue(String fieldName, Object value) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        PropertyDescriptor descriptor = new PropertyDescriptor(fieldName, clazz);
        Method method = descriptor.getWriteMethod();
        method.invoke(object, value);
    }

}
