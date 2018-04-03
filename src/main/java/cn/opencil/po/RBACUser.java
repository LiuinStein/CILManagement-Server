package cn.opencil.po;

import cn.opencil.validation.group.NotNullUserIdValidation;
import cn.opencil.validation.group.RegisterValidation;
import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * for t_rbac_user table
 */
public class RBACUser implements UserDetails {
    @Positive
    @NotNull(groups = {
            RegisterValidation.class,
            NotNullUserIdValidation.class
    })
    private Long id;

    @NotNull(groups = {
            RegisterValidation.class
    })
    @Size(min = 6, max = 20, groups = {
            RegisterValidation.class
    })
    private String password;

    @NotNull(groups = {
            RegisterValidation.class,
            NotNullUserIdValidation.class
    })
    private Boolean enabled;

    private List<GrantedAuthority> authorities = new ArrayList<>();

    public void setRole(String role) {
        authorities.add(new SimpleGrantedAuthority(role));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    ERROR: Illegal overloaded getter method with ambiguous type for property
//    The getter of a boolean value is isValueName rather than getValueName
//    public Boolean getEnabled() {
//        return enabled;
//    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled == null ? false : enabled;
    }
}
