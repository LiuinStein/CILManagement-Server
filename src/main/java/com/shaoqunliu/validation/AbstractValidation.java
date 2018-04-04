package com.shaoqunliu.validation;

/**
 * @author Shaoqun Liu
 */
public abstract class AbstractValidation implements ValidationAdapter {
    /**
     * When fail fast is enabled the validation will stop on the first constraint violation detected.
     * If you change the fail fast option, it will be take effect after you restart the tomcat container.
     */
    private boolean failFast = true;

    public void setFailFast(boolean failFast) {
        this.failFast = failFast;
    }

    public boolean isFailFast() {
        return failFast;
    }


    /**
     * @see com.shaoqunliu.validation.ValidationAdapter
     */
    @Override
    public abstract <T> T validate(T object, Class... groups) throws ValidationException;

}
