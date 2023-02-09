package com.tacos.config;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tacos.data.Registration;

@Component
public class PasswordValidator implements Validator {
 
    public boolean supports(Class<?> paramClass) {
        return Registration.class.equals(paramClass);
    }
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "valid.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConf", "valid.passwordConf");
        Registration form = (Registration) obj;
        if (!form.getPassword().equals(form.getPasswordConf())) {
            errors.rejectValue("passwordConf", "valid.passwordConfDiff");
        }
    }
}