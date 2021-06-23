package com.bci.msuser.constant;



public class SecurityConstant {


    public static final String LOGIN_URL = "/login";
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER= "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED="Token cannot be verified";
    public static final String TOKEN_ORIGIN="MS-USER token";
    public static final String FORBIDDEN_MESSAGE="Necesitas iniciar sesión para entrar a esta sección";
    public static final String ACCESS_DENIED_MESSAGE="No tienes acceso para ingresar a este lugar";
    public static final String OPTIONS_HTTP_METHOD="OPTIONS";
    public static final String[] PUBLIC_URL={"/user"};
    // JWT

    public static final String ISSUER_INFO = "https://www.autentia.com/";
    public static final String SUPER_SECRET_KEY = "1234";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000;
    public static final String SIGN_UP_URL = "/user";

    public static final String SECRET = "SECRET_KEY";

}
