package cn.opencil.service.implementation;

import cn.opencil.service.ValidationService;
import com.shaoqunliu.validation.AdaptersValidation;
import com.shaoqunliu.validation.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("myValidationService")
public class MyValidationService implements ValidationService {

    private final AdaptersValidation adaptersValidation;

    @Autowired
    public MyValidationService(AdaptersValidation adaptersValidation) {
        this.adaptersValidation = adaptersValidation;
    }

    @Override
    public <T> T validate(T object, Class<?>... groups) throws ValidationException {
        return adaptersValidation.validate(object, groups);
    }

}
