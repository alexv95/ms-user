package com.bci.msuser.constant;

public class RegexPatternValidators {
    public final static String MAIL_PATTERN= "\"^(.+)@(.+)$\"";
    public final static String PASSWORD_PATTERN= "(?=.*[A-Z])(?=.*[a-z])(?=.*?[0-9].*?[0-9])(?=.{8,})";
}
