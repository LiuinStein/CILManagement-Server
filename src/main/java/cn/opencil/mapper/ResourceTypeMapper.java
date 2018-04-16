package cn.opencil.mapper;

import cn.opencil.po.ResourceType;

public interface ResourceTypeMapper {

    Integer addResourceType(ResourceType resourceType);

    Integer deleteTypeOfResource(Integer id);
}
