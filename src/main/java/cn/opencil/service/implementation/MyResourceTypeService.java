package cn.opencil.service.implementation;

import cn.opencil.mapper.ResourceTypeMapper;
import cn.opencil.po.ResourceType;
import cn.opencil.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("myResourceTypeService")
public class MyResourceTypeService implements ResourceTypeService {

    private final ResourceTypeMapper typeMapper;

    @Autowired
    public MyResourceTypeService(ResourceTypeMapper typeMapper) {
        this.typeMapper = typeMapper;
    }


    @Override
    public boolean addResourceType(ResourceType resourceType) {
        return typeMapper.addResourceType(resourceType) == 1;
    }

    @Override
    public boolean deleteTypeOfResource(ResourceType resourceType) {
        return typeMapper.deleteTypeOfResource(resourceType.getId()) != 0;
    }
}
