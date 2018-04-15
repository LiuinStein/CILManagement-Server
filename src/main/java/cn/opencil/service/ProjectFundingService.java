package cn.opencil.service;

import cn.opencil.po.ProjectFunding;

import java.util.List;

public interface ProjectFundingService {

    boolean addFundingExpenditure(ProjectFunding funding);

    List<ProjectFunding> queryExpenditures(ProjectFunding funding);
}
