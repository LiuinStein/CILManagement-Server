package cn.opencil.mapper;

import cn.opencil.po.RBACUser;

public interface RBACUserMapper {
    RBACUser getUserByUserId(Long id);
}
