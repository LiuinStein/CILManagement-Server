package cn.opencil.filter;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;

import javax.servlet.*;
import java.io.IOException;

public class SecurityFilter extends AbstractSecurityInterceptor implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    public void destroy() {

    }

    public Class<?> getSecureObjectClass() {
        return null;
    }

    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return null;
    }
}
