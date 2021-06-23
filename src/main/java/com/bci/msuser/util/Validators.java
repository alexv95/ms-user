package com.bci.msuser.util;
import com.bci.msuser.constant.RegexPatternValidators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {
    public boolean mailValidator(String mail){
        return this.patternValidator(RegexPatternValidators.MAIL_PATTERN,mail);
    }

    public boolean passwordValidator(String password){
        return this.patternValidator(RegexPatternValidators.PASSWORD_PATTERN,password);

    }
    public boolean patternValidator(String validatorPattern,String parameterToValidate){
        Pattern pattern= Pattern.compile(validatorPattern);
        Matcher matcher = pattern.matcher(parameterToValidate);
        if(matcher.matches()){
            return true;
        }
        return false;
    }


}
