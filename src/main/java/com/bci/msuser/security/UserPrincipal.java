package com.bci.msuser.security;


import com.bci.msuser.model.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.stream.Collectors;

import java.util.Collection;
import static java.util.Arrays.stream;

public class UserPrincipal implements UserDetails {
    private UserModel userModel;
    public UserPrincipal(UserModel userModel) {
        this.userModel = userModel;
    }

    /**
     * gets user authorities as a collection
     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return stream(this.userModel.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    /**
     * the following methods gets an object {UserModel}  not null on the constructor
     * */
    @Override
    public String getPassword(){
        return userModel.getPassword();
    }
    @Override
    public String getUsername(){
        return userModel.getEmail();
    }

    /**
     * the following parameters point to isActive
     * for a real implementation it might be neccesary to add these values on user document
     * */
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
