package cn.opencil.mapper;

import cn.opencil.po.ProjectFunding;

public interface ProjectFundingMapper {

    Integer addFundingRow(ProjectFunding funding);

    Long checkProjectBalance(Integer projectId);
}
