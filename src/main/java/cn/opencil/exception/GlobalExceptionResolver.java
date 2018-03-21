package cn.opencil.exception;

import cn.opencil.vo.RestfulResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @NotNull
    @Override
    public ModelAndView resolveException(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @Nullable Object o, @NotNull Exception exception) {
        RestfulResult result = new RestfulResult(1, exception.getMessage(), new HashMap<>());
        if (exception instanceof SimpleException) {
            result.setCode(((SimpleException) exception).getCode());
        }
        try {
            if ("application/xml".equals(request.getHeader("Accept"))) {
                response.getWriter().print(result.toXmlString());
            } else {
                response.getWriter().print(result.toJsonString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView();
    }
}
