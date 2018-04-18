package cn.opencil.mapper;

import cn.opencil.po.ResourceUsage;

public interface ResourceUsageMapper {

    Integer addResourceUsage(ResourceUsage resourceUsage);

    Integer checkResourceRemaining(Integer id);
}
