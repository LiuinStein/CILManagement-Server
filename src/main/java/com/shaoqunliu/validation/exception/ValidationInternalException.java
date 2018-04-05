package com.shaoqunliu.validation.exception;

public class ValidationInternalException extends ValidationException {
    public ValidationInternalException(String message) {
        super("Internal Exception: " + message);
    }
}
