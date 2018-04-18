package cn.opencil.mapper;

import cn.opencil.po.Resource;
import cn.opencil.po.ResourceUsage;

import java.util.List;

public interface ResourceUsageMapper {

    Integer addResourceUsage(ResourceUsage resourceUsage);

    Resource checkResourceRemaining(Integer id);

    List<ResourceUsage> queryResourceUsage(ResourceUsage resourceUsage);
}
