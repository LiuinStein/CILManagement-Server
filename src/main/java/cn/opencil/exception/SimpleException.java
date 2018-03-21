package cn.opencil.exception;

public class SimpleException extends Exception {
    private long code;

    public SimpleException(int code, String message) {
        super(message);
        this.code = code;
    }

    public long getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
