package cn.opencil.service.implementation;

import cn.opencil.mapper.ResourceMapper;
import cn.opencil.mapper.ResourceUsageMapper;
import cn.opencil.po.Resource;
import cn.opencil.po.ResourceUsage;
import cn.opencil.service.ResourceUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("myResourceUsageService")
public class MyResourceUsageService implements ResourceUsageService {

    private final ResourceUsageMapper usageMapper;
    private final ResourceMapper resourceMapper;

    @Autowired
    public MyResourceUsageService(ResourceUsageMapper usageMapper, ResourceMapper resourceMapper) {
        this.usageMapper = usageMapper;
        this.resourceMapper = resourceMapper;
    }

    @Override
    public boolean rentResource(ResourceUsage resourceUsage) {
        Resource resource = usageMapper.checkResourceRemaining(resourceUsage.getResourceId());
        Integer remaining = resource.getRemaining() + resourceUsage.getAmount();
        if (remaining < 0 || remaining > resource.getQuantity()) {
            return false;
        }
        resource = new Resource();
        resource.setId(resourceUsage.getResourceId());
        resource.setRemaining(remaining);
        return resourceMapper.modifyResourceInfo(resource) == 1 &&
                usageMapper.addResourceUsage(resourceUsage) == 1;
    }
}
