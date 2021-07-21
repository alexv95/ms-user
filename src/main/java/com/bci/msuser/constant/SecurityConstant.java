package com.bci.msuser.constant;



public class SecurityConstant {

    public static final long EXPIRATION_TIME = 600_000; // 10 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String GET_ARRAYS_LLC = "Get user,LLC";
    public static final String GET_ARRAYS_ADMINISTRATION = "User authorization";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = { "/user","/user/login" };


}
