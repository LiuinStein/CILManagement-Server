package cn.opencil.security;

import cn.opencil.vo.RestfulResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityRestfulResponsePrinter {

    public void printXmlResult(HttpServletResponse response, RestfulResult result) throws IOException {
        response.setHeader("Content-Type", "application/xml");
        response.getWriter().print(result.toXmlString());
    }

    public void printJsonResult(HttpServletResponse response, RestfulResult result) throws IOException {
        response.setHeader("Content-Type", "application/json");
        response.getWriter().print(result.toJsonString());
    }

    public void print(HttpServletRequest request, HttpServletResponse response, RestfulResult result) throws IOException {
        if ("application/xml".equals(request.getHeader("Accept"))) {
            printXmlResult(response, result);
        } else if ("application/json".equals(request.getHeader("Accept"))) {
            printJsonResult(response, result);
        } else {
            throw new IOException("an unsupported response format");
        }
    }

}
