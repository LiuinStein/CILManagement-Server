package cn.opencil.exception;

import org.springframework.http.HttpStatus;

public class SimpleHttpException extends SimpleException {

    private HttpStatus httpStatusToReturn;

    public SimpleHttpException(int code, String message, HttpStatus httpStatus) {
        super(code, message);
        httpStatusToReturn = httpStatus;
    }

    public HttpStatus getHttpStatusToReturn() {
        return httpStatusToReturn;
    }

    public void setHttpStatusToReturn(HttpStatus httpStatusToReturn) {
        this.httpStatusToReturn = httpStatusToReturn;
    }
}
