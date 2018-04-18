package cn.opencil.mapper;

import cn.opencil.po.ResourceType;

import java.util.List;

public interface ResourceTypeMapper {

    Integer addResourceType(ResourceType resourceType);

    Integer deleteTypeOfResource(Integer id);

    Integer modifyResourceTypeProperties(ResourceType resourceType);

    List<ResourceType> queryTypesByResourceId(Integer id);

    List<ResourceType> queryTypesByTypeId(Integer id);
}
