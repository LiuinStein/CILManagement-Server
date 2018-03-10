package cn.opencil.security;

import cn.opencil.mapper.RBACPermissionRoleMapper;
import cn.opencil.po.RBACPermissionRole;
import com.shaoqunliu.security.SecurityComponentException;
import com.shaoqunliu.security.util.BasicHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.*;

public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {


    /**
     * The resources to permissions map
     * Make sure it's static:
     * If the system administer modify the permissions,
     * we can modify this map through creating an instance of this class and invoking the method refreshPermissions.
     * because, the map is static, no matter how much instances of this class has been created,
     * there is only one copy of this map stored in the memory, so, we can modify it easily in anywhere
     */
    private static Map<BasicHttpRequest, Collection<ConfigAttribute>> resourcePermissionMap;

    private final RBACPermissionRoleMapper permissionRoleMapper;

    @Autowired
    public MySecurityMetadataSource(RBACPermissionRoleMapper permissionRoleMapper) {
        // only invoke once when tomcat container start
        this.permissionRoleMapper = permissionRoleMapper;
        refreshPermissions();
    }

    /**
     * Refresh the permission table
     */
    public void refreshPermissions() {
        // get resource permission map from database
        resourcePermissionMap = new HashMap<>();
        List<RBACPermissionRole> permissionRoles = permissionRoleMapper.getPermissionRoleMapper();
        for (RBACPermissionRole permissionRole : permissionRoles) {
            if (resourcePermissionMap.containsKey(permissionRole.getRequest())) {
                resourcePermissionMap.get(permissionRole.getRequest()).add(new SecurityConfig(permissionRole.getName()));
            } else {
                Collection<ConfigAttribute> configs = new ArrayList<>();
                configs.add(new SecurityConfig(permissionRole.getName()));
                resourcePermissionMap.put(permissionRole.getRequest(), configs);
            }
//            resourcePermissionMap.put(permissionRole.getRequest(), )
        }
    }

    /**
     * Get the permission of URL
     *
     * @param object is an instance of FilterInvocation, included a HttpRequest
     * @return all of the permissions of this URL
     * @throws IllegalArgumentException when the input argument is illegal
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        try {
            BasicHttpRequest request = new BasicHttpRequest(filterInvocation.getRequest());
            for (BasicHttpRequest iterateRequest : resourcePermissionMap.keySet()) {
                if (request.match(iterateRequest)) {
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
