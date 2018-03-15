package cn.opencil.vo;

import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class RestfulResult {

    private long code;
    private String message;
    private Map<String, Object> data;

    public RestfulResult(long code, String message, Map<String, Object> data) {
        this.code = code;
        this.message = message;
        this.data = data == null ? new HashMap<>() : data;
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

    public void setData(Map<String, Object> data) {
        this.data = data == null ? new HashMap<>() : data;
    }

    /**
     * Transfer this object to a JSON String
     *
     * @return a JSON String converted from this object
     * @apiNote require com.google.gson dependency
     */
    public String toJsonString() {
        return new GsonBuilder().enableComplexMapKeySerialization().create().toJson(this);
    }

    /**
     * Transfer this object to an XML String
     * it only provides limited ability to XML transformation.
     * do NOT use it with a complex data structure and MAKE SURE all of the data objects have overwritten the toString method
     * I wrote this only for Spring Security Authorization Handle Response
     * You can use Spring REST in your SpringMVC project to transfer this to XML instead of using this method
     *
     * @return an empty string
     */
    public String toXmlString() {
        StringBuilder result = new StringBuilder(200);
        result.append("<RestfulResult>")
                .append("<code>").append(code).append("</code>")
                .append("<message>").append(message).append("</message>")
                .append("<data>");
        for (String key : data.keySet()) {
            result.append("<").append(key).append(">")
                    .append(data.get(key).toString())
                    .append("</").append(key).append(">");
        }
        result.append("</data>")
                .append("</RestfulResult>");
        return result.toString();
    }
}
