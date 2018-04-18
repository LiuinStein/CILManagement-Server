package cn.opencil.mapper;

import cn.opencil.po.Resource;
import cn.opencil.po.ResourceUsage;

public interface ResourceUsageMapper {

    Integer addResourceUsage(ResourceUsage resourceUsage);

    Resource checkResourceRemaining(Integer id);
}
