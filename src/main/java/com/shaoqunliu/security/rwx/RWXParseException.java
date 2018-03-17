package com.shaoqunliu.security.rwx;

/**
 * The specify exception class for RWX permission.
 * throw it when parse any-type code that represented the permission to SinglePermission
 * @author <a href="https://www.shaoqunliu.cn/">Shaoqun Liu</a>
 * @since March 4, 2018
 */
public class RWXParseException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message.
     */
    public RWXParseException(String message) {
        super(message);
    }
}
