package cn.opencil.vo;

import com.shaoqunliu.rest.AbstractRestfulResult;

import java.util.Map;

public class RestfulResult extends AbstractRestfulResult {

    private long code;
    private String message;

    public RestfulResult(long code, String message, Map<String, Object> data) {
        super(data);
        this.code = code;
        this.message = message;
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

    /**
     * Transfer this object to an XML String
     * it only provides limited ability to XML transformation.
     * do NOT use it with a complex data structure and MAKE SURE all of the data objects have overwritten the toString method
     * I wrote this only for Spring Security Authorization Handle Response
     * You can use Spring REST in your SpringMVC project to transfer this to XML instead of using this method
     *
     * @return a XML String converted from this object
     */
    public String toXmlString() {
        return "<RestfulResult>" +
                "<code>" + code + "</code>" +
                "<message>" + message + "</message>" +
                toXmlString("data") +
                "</RestfulResult>";
    }
}
