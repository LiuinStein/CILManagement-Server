package cn.opencil.service.implementation;

import cn.opencil.mapper.ProjectFundingMapper;
import cn.opencil.po.ProjectFunding;
import cn.opencil.service.ProjectFundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myProjectFundingService")
public class MyProjectFundingService implements ProjectFundingService {

    private final ProjectFundingMapper fundingMapper;

    @Autowired
    public MyProjectFundingService(ProjectFundingMapper fundingMapper) {
        this.fundingMapper = fundingMapper;
    }

    @Override
    public boolean addFundingExpenditure(ProjectFunding funding) {
        Long balance = fundingMapper.checkProjectBalance(funding.getProjectId());
        if ((balance == null && funding.getAmount() < 0) ||
                (balance != null && balance + funding.getAmount() < 0)) {
            return false;
        }
        funding.setBalance(balance == null ? funding.getAmount() : Long.valueOf(balance + funding.getAmount()));
        return fundingMapper.addFundingRow(funding) == 1;
    }

    @Override
    public List<ProjectFunding> queryExpenditures(ProjectFunding funding) {
        return fundingMapper.queryExpenditures(funding.getProjectId());
    }
}
