package com.shaoqunliu.validation;

import com.shaoqunliu.validation.exception.ValidationException;

/**
 * Validate with specific adapters
 *
 * @author Shaoqun Liu
 * @since 1.8
 */
public class AdaptersValidation extends AbstractValidation {

    /**
     * the validation adapters
     */
    private ValidationAdapter[] adapters = {};

    /**
     * @see com.shaoqunliu.validation.ValidationAdapter#validate(Object, Class[])
     */
    @Override
    public <T> T validate(T object, Class<?>... groups) throws ValidationException {
        StringBuilder message = new StringBuilder(64);
        for (ValidationAdapter adapter : adapters) {
            try {
                adapter.validate(object, groups);
            } catch (ValidationException e) {
                if (isFailFast()) {
                    throw e;
                }
                message.append(e.getMessage()).append("; ");
            }
        }
        if (message.length() != 0) {
            throw new ValidationException(message.toString());
        }
        return object;
    }

    public ValidationAdapter[] getAdapters() {
        return adapters;
    }

    public void setAdapters(ValidationAdapter[] adapters) {
        this.adapters = adapters;
    }
}
