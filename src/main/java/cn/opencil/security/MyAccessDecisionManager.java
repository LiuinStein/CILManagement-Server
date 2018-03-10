package cn.opencil.security;

import org.springframework.http.HttpRequest;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;

public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * Check if user have enough permission to access to the resource
     *
     * @param authentication is the user's permission
     * @param object         is an instance of FilterInvocation, can use getHttpRequest method to transform to HttpRequest
     * @param collection     is the returned value of MySecurityMetadataSource.getAttributes
     * @throws AccessDeniedException               when user access is denied, this exception is visible in server, the server will show a 403 page to user
     * @throws InsufficientAuthenticationException when user authentication is insufficient
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        if (collection == null) {
            return;
        }
        for (ConfigAttribute config : collection) {
            String neededRole = config.getAttribute();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (neededRole.equals(authority.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("Access Denied to page");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
