package cn.opencil.security;

import cn.opencil.vo.RestfulResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyAuthenticationSuccessHandle implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // run to here when log in success
        Map<String, Object> authorization = new HashMap<>();
        authorization.put("auth", authentication.getAuthorities().toString());
        RestfulResult result = new RestfulResult(0, "log in success", authorization);
        SecurityRestfulResponsePrinter responseHandle = new SecurityRestfulResponsePrinter();
        responseHandle.print(request, response, result);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }
}
