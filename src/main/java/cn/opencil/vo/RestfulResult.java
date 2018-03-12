package cn.opencil.vo;

import java.util.ArrayList;

public class RestfulResult<T> {

    private long code;
    private String message;
    private ArrayList<T> data;

    public RestfulResult(long code, String message, ArrayList<T> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }
}
