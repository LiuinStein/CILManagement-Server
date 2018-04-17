package cn.opencil.service.implementation;

import cn.opencil.mapper.ResourceMapper;
import cn.opencil.po.Resource;
import cn.opencil.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
