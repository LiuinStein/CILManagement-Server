package cn.opencil.security;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyLogoutFilter extends LogoutFilter {

    private String method = "POST";

    public MyLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler... handlers) {
        super(logoutSuccessHandler, handlers);
    }

    @Override
    protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
        return method.equals(request.getMethod()) && super.requiresLogout(request, response);
    }

    public void setMethod(String method) {
        this.method = method.toUpperCase();
    }
}
