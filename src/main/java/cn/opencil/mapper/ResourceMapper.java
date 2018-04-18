package cn.opencil.mapper;

import cn.opencil.po.Resource;

import java.util.List;

public interface ResourceMapper {

    Integer addResource(Resource resource);

    Integer deleteResource(Integer id);

    Integer modifyResourceInfo(Resource resource);

    List<Resource> queryResourceInfo(Resource resource);
}
