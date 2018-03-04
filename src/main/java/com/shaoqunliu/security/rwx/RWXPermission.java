package com.shaoqunliu.security.rwx;

public class RWXPermission {
    private SinglePermission readable = new SinglePermission();
    private SinglePermission writable = new SinglePermission();
    private SinglePermission executable = new SinglePermission();

    private int checkCode(int code) throws RWXParseException {
        if (code < 0 || code > 7) {
            throw new RWXParseException("Illegal input permission code");
        } else {
            return code;
        }
    }

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

    public RWXPermission(String code) throws RWXParseException {
        init(code);
    }

    public RWXPermission(Integer code) throws RWXParseException {
        init(code.toString());
    }

    public SinglePermission allowRead() {
        return readable;
    }

    public SinglePermission allowWrite() {
        return writable;
    }

    public SinglePermission allowExcute() {
        return executable;
    }

}
