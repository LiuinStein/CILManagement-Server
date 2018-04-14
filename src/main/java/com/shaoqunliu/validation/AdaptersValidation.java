package com.shaoqunliu.validation;

import com.shaoqunliu.validation.exception.ValidationException;

import java.util.List;

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
    private final List<ValidationAdapter> adapters;

    public AdaptersValidation(List<ValidationAdapter> adapters) {
        this.adapters = adapters;
    }

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

    /**
     * Make all of the adapters has similar fail fast property
     *
     * @param failFast when fail fast is enabled the validation will stop on the first constraint violation detected.
     */
    @Override
    public void setFailFast(boolean failFast) {
        super.setFailFast(failFast);
        for (ValidationAdapter adapter : adapters) {
            if (adapter instanceof AbstractValidation) {
                ((AbstractValidation) adapter).setFailFast(failFast);
            }
        }
    }

    public List<ValidationAdapter> getAdapters() {
        return adapters;
    }

    /**
     * Add an adapter dynamically
     *
     * @param adapter the adapter need to add
     * @return true if add successfully, otherwise false
     */
    public boolean addAdapter(ValidationAdapter adapter) {
        return adapters.add(adapter);
    }
}
