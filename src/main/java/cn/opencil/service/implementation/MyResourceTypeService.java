package cn.opencil.service.implementation;

import cn.opencil.mapper.ResourceTypeMapper;
import cn.opencil.po.ResourceType;
import cn.opencil.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public boolean modifyResourceTypeProperties(ResourceType resourceType) {
        return typeMapper.modifyResourceTypeProperties(resourceType) == 1;
    }

    @Override
    public List<ResourceType> queryTypesByResourceId(Integer id) {
        return typeMapper.queryTypesByResourceId(id);
    }

    @Override
    public List<ResourceType> queryTypesByTypeId(Integer id) {
        return typeMapper.queryTypesByTypeId(id);
    }
}
