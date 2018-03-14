package cn.opencil.po;

import com.shaoqunliu.security.SecurityComponentException;
import com.shaoqunliu.security.util.BasicHttpRequest;

public class RBACPermissionRole {
//    private String url;
//    private Integer method;
    private BasicHttpRequest request = new BasicHttpRequest();
    private String name;

    public String getUrl() {
        return request.getUrl();
    }

    public void setUrl(String url) {
        request.setUrl(url);
    }

    public Integer getMethod() {
        return request.getIntMethod();
    }

    public void setMethod(Integer method) throws SecurityComponentException {
        request.setIntMethod(method);
    }

    public String getName() {
        return name == null ? "anonymous" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BasicHttpRequest getRequest() {
        return request;
    }
}
