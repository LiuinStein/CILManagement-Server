package cn.opencil.service;

import cn.opencil.po.ResourceType;

import java.util.List;

public interface ResourceTypeService {

    boolean addResourceType(ResourceType resourceType);

    boolean deleteTypeOfResource(ResourceType resourceType);

    boolean modifyResourceTypeProperties(ResourceType resourceType);

    List<ResourceType> queryTypesByResourceId(Integer id);

    List<ResourceType> queryTypesByTypeId(Integer id);
}
