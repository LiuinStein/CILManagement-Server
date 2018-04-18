package cn.opencil.service;

import cn.opencil.po.ResourceUsage;

import java.util.List;

public interface ResourceUsageService {

    boolean rentResource(ResourceUsage resourceUsage);

    List<ResourceUsage> queryResourceUsage(ResourceUsage resourceUsage);
}
