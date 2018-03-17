package com.shaoqunliu.security.rwx;

/**
 * The {@code RWXPermission} class represents the 3 section of rwx permission.
 * @author <a href="https://www.shaoqunliu.cn/">Shaoqun Liu</a>
 * @since March 4, 2018
 */
public class RWXPermission {
    private SinglePermission readable = new SinglePermission();
    private SinglePermission writable = new SinglePermission();
    private SinglePermission executable = new SinglePermission();

    /**
     * Check the code to be legal
     * @param code the single digit of permission code
     * @return the {@code code} if the digit is legal, otherwise throw RWXParseException
     * @throws RWXParseException when the {@code code} is negative or greater than 7
     */
    private int checkCode(int code) throws RWXParseException {
        if (code < 0 || code > 7) {
            throw new RWXParseException("Illegal input permission code");
        } else {
            return code;
        }
    }

    /**
     * Use the 3 permission codes to generate 3 SinglePermission objects
     * @param left the permission code for the owner
     * @param mid the permission code for the owner's group
     * @param right the permission code for the others
     * @throws RWXParseException if there is a illegal code
     */
    private void code2permission(int left, int mid, int right) throws RWXParseException {
        left = checkCode(left); // your own
        mid = checkCode(mid); // in your groups
        right = checkCode(right); // for others
        readable.setYourOwn(left / 4 == 1);
        readable.setInGroup(mid / 4 == 1);
        readable.setOthers(right / 4 == 1);
        left -= left >= 4 ? 4 : 0;
        mid -= mid >= 4 ? 4 : 0;
        right -= right >= 4 ? 4 : 0;
        writable.setYourOwn(left / 2 == 1);
        writable.setInGroup(mid / 2 == 1);
        writable.setOthers(right / 2 == 1);
        left -= left >= 2 ? 2 : 0;
        mid -= mid >= 2 ? 2 : 0;
        right -= right >= 2 ? 2 : 0;
        executable.setYourOwn(left == 1);
        executable.setInGroup(mid == 1);
        executable.setOthers(right == 1);
    }

    /**
     * Initialize the class
     * @param code the permission code as a String
     * @throws RWXParseException if there is a illegal code
     */
    private void init(String code) throws RWXParseException {
        try {
            int left = code.charAt(0) - '0';
            int mid = code.charAt(1) - '0';
            int right = code.charAt(2) - '0';
            code2permission(left, mid, right);
        } catch (StringIndexOutOfBoundsException e) {
            throw new RWXParseException("Cannot parse the input permission code");
        }
    }

    /**
     * Use String code to construct this class
     * @param code the permission code as a String
     * @throws RWXParseException if there is a illegal code
     */
    public RWXPermission(String code) throws RWXParseException {
        init(code);
    }

    /**
     * Use Integer code to construct this class
     * @param code the permission code as an Integer
     * @throws RWXParseException if there is a illegal code
     */
    public RWXPermission(Integer code) throws RWXParseException {
        init(code.toString());
    }

    /**
     * @return the readable property
     */
    public SinglePermission allowRead() {
        return readable;
    }

    /**
     * @return the writable property
     */
    public SinglePermission allowWrite() {
        return writable;
    }

    /**
     * @return the executable property
     */
    public SinglePermission allowExecute() {
        return executable;
    }

}
