package com.bci.msuser.security;


import com.bci.msuser.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public class UserPrincipal implements UserDetails {
    private UserModel userModel;
    public UserPrincipal(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return null;
    }
    @Override
    public String getPassword(){
        return userModel.getPassword();
    }
    @Override
    public String getUsername(){
        return userModel.getEmail();
    }
    @Override
    public boolean isAccountNonExpired(){
        return userModel.getIsActive();
    }
    @Override
    public boolean isAccountNonLocked(){
        return userModel.getIsActive();
    }
    @Override
    public boolean isCredentialsNonExpired(){
        return userModel.getIsActive();
    }
    @Override
    public boolean isEnabled(){
        return userModel.getIsActive();
    }


}
