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
        if (request.getHeader("Accept").toLowerCase().contains("application/xml")) {
            printXmlResult(response, result);
        } else if (request.getHeader("Accept").toLowerCase().contains("application/json")) {
            printJsonResult(response, result);
        } else {
            throw new IOException("unsupported response format was given");
        }
    }

}
