package cn.opencil.security;

import cn.opencil.vo.RestfulResult;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class MyFailureHandle implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        RestfulResult result = new RestfulResult(1, e.getMessage(), new HashMap<>());
        SecurityRestfulResponsePrinter responseHandle = new SecurityRestfulResponsePrinter();
        responseHandle.print(request, response, result);
        // run to here when log in failed
        if (e instanceof InternalAuthenticationServiceException) {
            // an InternalAuthenticationServiceException will be threw when username is not existent
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
