package cn.opencil.service;

import cn.opencil.po.Resource;

public interface ResourceService {

    boolean addResource(Resource resource);

    boolean deleteResource(Resource resource);
}
