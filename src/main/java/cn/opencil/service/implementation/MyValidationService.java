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
    private final ValidationAdapter databaseValidationAdapter;

    @Autowired
    public MyValidationService(@Qualifier("simpleValidation") ValidationAdapter validationAdapter,
                               @Qualifier("foreignKeyValidation") ValidationAdapter databaseValidationAdapter) {
        this.normalValidationAdapter = validationAdapter;
        this.databaseValidationAdapter = databaseValidationAdapter;
    }

    @Override
    public <T> T validate(T object, Class... groups) throws ValidationException {
        return databaseValidationAdapter.validate(normalValidationAdapter.validate(object, groups), groups);
    }

}
