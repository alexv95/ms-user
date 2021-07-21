package com.bci.msuser.constant;

public class RegexPatternValidators {
    //old pattern  ^(.+)@(.+)$
    public final static String MAIL_PATTERN="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public final static String PASSWORD_PATTERN= "^.*(?=.*[A-Z])(?=.*[0-9].*[0-9])(?=.*[a-z]).*$";

}
