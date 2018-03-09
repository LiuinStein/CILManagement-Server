package com.shaoqunliu.security.util;

import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import com.shaoqunliu.security.SecurityComponentException;

public class BasicHttpRequest {

    private String url;
    private Integer nMethod;

    public static HttpMethod intMethod2HttpMethod(Integer method) {
        switch (method) {
            case 0:
                return HttpMethod.GET;
            case 1:
                return HttpMethod.HEAD;
            case 2:
                return HttpMethod.POST;
            case 3:
                return HttpMethod.PUT;
            case 4:
                return HttpMethod.PATCH;
            case 5:
                return HttpMethod.DELETE;
            case 6:
                return HttpMethod.OPTIONS;
            case 7:
                return HttpMethod.TRACE;
            default:
                return null;
        }
    }

    public static Integer httpMethod2IntMethod(HttpMethod method) {
        // I just want to make it safe
        switch (method) {
            case GET:
                return 0;
            case HEAD:
                return 1;
            case POST:
                return 2;
            case PUT:
                return 3;
            case PATCH:
                return 4;
            case DELETE:
                return 5;
            case OPTIONS:
                return 6;
            case TRACE:
                return 7;
            default:
                return -1;
        }
        // do not use enum.ordinal method here!
        // return method.ordinal();
    }

    public static Integer stringMethod2IntMethod(String method) {
        switch (method.toUpperCase()) {
            case "GET":
                return 0;
            case "HEAD":
                return 1;
            case "POST":
                return 2;
            case "PUT":
                return 3;
            case "PATCH":
                return 4;
            case "DELETE":
                return 5;
            case "OPTIONS":
                return 6;
            case "TRACE":
                return 7;
            default:
                return -1;
        }
    }

    public BasicHttpRequest(String url, Integer method) {
        this.url = url;
        this.nMethod = method;
    }

    public BasicHttpRequest(String url, HttpMethod method) {
        this.url = url;
        this.nMethod = httpMethod2IntMethod(method);
    }

    public BasicHttpRequest(HttpServletRequest servletRequest) throws SecurityComponentException {
        if (servletRequest == null) {
            throw new SecurityComponentException("ERROR: HttpServletRequest, can not construct");
        }
        this.url = servletRequest.getRequestURI();
        this.nMethod = stringMethod2IntMethod(servletRequest.getMethod());
    }

    public boolean match(BasicHttpRequest obj) {
        return obj.nMethod.equals(this.nMethod) &&
                (new AntPathMatcher()).match(obj.url, this.url);
    }
}
