package cn.opencil.service;

import com.shaoqunliu.validation.exception.ValidationException;

public interface ValidationService {
    <T> T validate(T object, Class... groups) throws ValidationException;

//    <T> T validateWithDatabase(T object, Class... groups) throws ValidationException;
}
