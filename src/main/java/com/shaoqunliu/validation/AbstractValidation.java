package com.shaoqunliu.validation;

import com.shaoqunliu.validation.exception.ValidationException;
import com.shaoqunliu.validation.exception.ValidationInternalException;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
     * @see com.shaoqunliu.validation.ValidationAdapter#validate(Object, Class[])
     */
    @Override
    public abstract <T> T validate(T object, Class<?>... groups) throws ValidationException;

    /**
     * @see com.shaoqunliu.validation.ValidationAdapter#validate(Object[], Predicate)
     */
    @Override
    public <T> T[] validate(T[] objects, Predicate<? super T> predicate) throws ValidationException {
        Stream<T> stream = Arrays.stream(objects);
        boolean allValid = isFailFast() ? stream.anyMatch(predicate.negate())
                : stream.allMatch(predicate);
        if (allValid) {
            return objects;
        }
        throw new ValidationException("invalid array");
    }

    /**
     * Check if the equivalence class is valid
     *
     * @param groups equivalence class groups
     * @throws ValidationInternalException when the input class group is null or the class is not an interface
     */
    protected void sanityCheckGroups(Class<?>[] groups) throws ValidationInternalException {
        Contracts.assertNotNull(groups, "Groups is null");
        for (Class<?> clazz : groups) {
            if (clazz == null || !clazz.isInterface()) {
                throw new ValidationInternalException("illegal argument");
            }
        }
    }

}
