package cn.opencil.service.implementation;

import cn.opencil.service.ValidationService;
import com.shaoqunliu.validation.ValidationAdapter;
import com.shaoqunliu.validation.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("myValidationService")
public class MyValidationService implements ValidationService {

    private final ValidationAdapter normalValidationAdapter;

    @Autowired
    public MyValidationService(@Qualifier("adaptersValidation") ValidationAdapter validationAdapter) {
        this.normalValidationAdapter = validationAdapter;
    }

    @Override
    public <T> T validate(T object, Class<?>... groups) throws ValidationException {
        return normalValidationAdapter.validate(object, groups);
    }

}
