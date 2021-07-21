package com.bci.msuser.enumeration;

import static com.bci.msuser.constant.Authority.*;


public enum Role {
    ROLE_USER(USER_AUTHORITIES);

    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}
