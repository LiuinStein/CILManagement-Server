package cn.opencil.exception;

import cn.opencil.vo.RestfulResult;
import com.shaoqunliu.validation.ValidationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class GlobalExceptionResolver implements HandlerExceptionResolver {
    /**
     * Resolve the exception form controller
     *
     * @param request   http request
     * @param response  http response
     * @param o         the executed handler, or null if none chosen at the time of the exception (for example, if multipart resolution failed)
     * @param exception the exception that threw from controller
     * @return a new ModelAndView
     * @implNote https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/HandlerExceptionResolver.html
     */
    @NotNull
    @Override
    public ModelAndView resolveException(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @Nullable Object o, @NotNull Exception exception) {
        RestfulResult result = new RestfulResult(1, exception.getMessage(), new HashMap<>());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        if (exception instanceof SimpleException) {
            result.setCode(((SimpleException) exception).getCode());
        }
        if (exception instanceof SimpleHttpException) {
            response.setStatus(((SimpleHttpException) exception).getHttpStatusToReturn().value());
        }
        if (exception instanceof HttpRequestMethodNotSupportedException) {
            result.setCode(405);
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        }
        if (exception instanceof ValidationException ||
                exception instanceof ServletRequestBindingException ||
                exception instanceof IllegalArgumentException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        if (exception instanceof DataAccessException) {
            result.setMessage("database access error");
        }
        try {
            if ("application/xml".equals(request.getHeader("Accept"))) {
                response.setHeader("Content-Type", "application/xml;charset=UTF-8");
                response.getWriter().print(result.toXmlString());
            } else {
                response.setHeader("Content-Type", "application/json;charset=UTF-8");
                response.getWriter().print(result.toJsonString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }
}
