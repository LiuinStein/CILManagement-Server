package cn.opencil.service;

import cn.opencil.po.Resource;

import java.util.List;

public interface ResourceService {

    boolean addResource(Resource resource);

    boolean deleteResource(Resource resource);

    boolean modifyResourceInfo(Resource resource);

    List<Resource> queryResourceInfo(Resource resource);
}
