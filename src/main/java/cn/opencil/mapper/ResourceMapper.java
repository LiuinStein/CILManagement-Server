package cn.opencil.mapper;

import cn.opencil.po.Resource;

public interface ResourceMapper {

    Integer addResource(Resource resource);

    Integer deleteResource(Integer id);

    Integer modifyResourceInfo(Resource resource);
}
