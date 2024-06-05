package org.achareh.utility;


import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class EntityValidator {
    public static final ValidatorFactory validatorFactory = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator()).buildValidatorFactory();

    public static final Validator validator = validatorFactory.usingContext()
            .messageInterpolator(new ParameterMessageInterpolator()).getValidator();
}
