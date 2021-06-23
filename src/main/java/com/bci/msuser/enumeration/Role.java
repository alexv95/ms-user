package com.bci.msuser.enumeration;

public class Role {


    ROLE_USER(USER_AUTHORITIES);
    public static final String[] USER_AUTHORITIES = { "user:read" };
    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}
