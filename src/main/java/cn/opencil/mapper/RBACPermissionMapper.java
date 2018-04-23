package cn.opencil.mapper;

import cn.opencil.po.RBACPermissionRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RBACPermissionMapper {

    List<RBACPermissionRole> getPermissionByUserId(@Param("id") Long id, @Param("isAll") Boolean isAll);

    List<RBACPermissionRole> getPermissionByRoleId(@Param("id") Integer id, @Param("isAll") Boolean isAll);
}
