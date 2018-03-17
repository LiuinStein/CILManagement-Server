package com.shaoqunliu.rest;

import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRestfulResult {
    /**
     * Make sure this value is NOT NULL
     */
    protected Map<String, Object> data;

    public AbstractRestfulResult(Map<String, Object> data) {
        this.data = data == null ? new HashMap<>() : data;
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
     * Make sure the object that want to be transferred can be convert to String or has overwritten the toString method
     *
     * @param rootTag is the name of XML root tag
     * @return a XML String converted from this object
     */
    protected String toXmlString(String rootTag) {
        StringBuilder result = new StringBuilder();
        result.append("<").append(rootTag).append(">");
        for (String key : data.keySet()) {
            result.append("<").append(key).append(">")
                    .append(data.get(key))
                    .append("</").append(key).append(">");
        }
        result.append("</").append(rootTag).append(">");
        return result.toString();
    }

}
