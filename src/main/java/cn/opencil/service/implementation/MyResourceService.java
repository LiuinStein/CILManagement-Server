package cn.opencil.service.implementation;

import cn.opencil.mapper.ResourceMapper;
import cn.opencil.po.Resource;
import cn.opencil.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myResourceService")
public class MyResourceService implements ResourceService {

    private final ResourceMapper resourceMapper;

    @Autowired
    public MyResourceService(ResourceMapper resourceMapper) {
        this.resourceMapper = resourceMapper;
    }

    @Override
    public boolean addResource(Resource resource) {
        return resourceMapper.addResource(resource) == 1;
    }

    @Override
    public boolean deleteResource(Resource resource) {
        return resourceMapper.deleteResource(resource.getId()) != 0;
    }

    @Override
    public boolean modifyResourceInfo(Resource resource) {
        return resourceMapper.modifyResourceInfo(resource) == 1;
    }

    @Override
    public List<Resource> queryResourceInfo(Resource resource) {
        return resourceMapper.queryResourceInfo(resource);
    }
}
