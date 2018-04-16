package cn.opencil.service;

import cn.opencil.po.ResourceType;

public interface ResourceTypeService {

    boolean addResourceType(ResourceType resourceType);

    boolean deleteTypeOfResource(ResourceType resourceType);

    boolean modifyResourceTypeProperties(ResourceType resourceType);
}
