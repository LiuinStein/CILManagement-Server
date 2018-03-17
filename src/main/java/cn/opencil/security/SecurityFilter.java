package cn.opencil.security;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;

public class SecurityFilter extends AbstractSecurityInterceptor implements Filter {

    // inject from xml configuration
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // all access will be filtered by this filter after log in
        invoke(new FilterInvocation(servletRequest, servletResponse, filterChain));
    }

    private void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        // beforeInvocation will invoke MySecurityMetadataSource.getAttributes get the permission of filtered URL
        // then, it'll invoke MyAccessDecisionManager.decide to check if the accession has enough permissions
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        // thereafter, do the next filter
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    public void destroy() {

    }

    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return securityMetadataSource;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }
}
