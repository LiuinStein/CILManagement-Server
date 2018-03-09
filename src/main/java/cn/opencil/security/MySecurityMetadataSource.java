package cn.opencil.security;

import com.shaoqunliu.security.SecurityComponentException;
import com.shaoqunliu.security.util.BasicHttpRequest;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Map<BasicHttpRequest, Collection<ConfigAttribute>> resourcePermissionMap = null;


    public MySecurityMetadataSource() {
        // only invoke once when tomcat container start
        loadPermissions();
    }

    private void loadPermissions() {
        // get resource permission map from database


    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // get the permission of URL
        FilterInvocation filterInvocation = (FilterInvocation) object;
        BasicHttpRequest request = null;
        try {
            request = new BasicHttpRequest(filterInvocation.getRequest());
            for (BasicHttpRequest iterateRequest : resourcePermissionMap.keySet()) {
                if (request.match(iterateRequest)) {
                    // return all of the permissions of this URL
                    return resourcePermissionMap.get(iterateRequest);
                }
            }
        } catch (SecurityComponentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
