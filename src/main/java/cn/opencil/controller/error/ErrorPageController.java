package cn.opencil.controller.error;

import cn.opencil.vo.RestfulResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class ErrorPageController {

    /**
     * Handle all of the client errors (4xx) and the server errors (5xx)
     * @param request the current http request
     * @return RestfulResult
     */
    @RequestMapping("/error/")
    public RestfulResult errorHandle(HttpServletRequest request) {
        String message = (String) request.getAttribute("javax.servlet.error.message");
        int statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return new RestfulResult(statusCode, message, new HashMap<>());
    }

    /**
     * Handle the exceptions from other components except for controller (such as Spring Security)
     * @param request the current http request
     * @return RestfulResult
     */
    @RequestMapping("/exception/")
    public RestfulResult exceptionHandle(HttpServletRequest request) {
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        int statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return new RestfulResult(statusCode, exception.getMessage(), new HashMap<>());
    }

}
