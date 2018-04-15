package cn.opencil.mapper;

import cn.opencil.po.ProjectFunding;

import java.util.List;

public interface ProjectFundingMapper {

    Integer addFundingRow(ProjectFunding funding);

    Long checkProjectBalance(Integer projectId);

    List<ProjectFunding> queryExpenditures(Integer projectId);
}
