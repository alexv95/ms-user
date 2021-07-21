
package com.bci.msuser.controller;

import com.bci.msuser.dto.LoginDTO;
import com.bci.msuser.dto.TokenDTO;
import com.bci.msuser.dto.UserDTO;
import com.bci.msuser.dto.UserOutputDTO;
import com.bci.msuser.exception.customerrors.EmailExistException;
import com.bci.msuser.exception.customerrors.EmailNotFoundException;
import com.bci.msuser.exception.customerrors.ValidatorErrorException;
import com.bci.msuser.security.UserPrincipal;
import com.bci.msuser.service.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Validated
public class UserController{

    private UserService userService;


    @Autowired
    public UserController(@Qualifier("userDetailsService") UserService userService ){
        this.userService=userService;

    }

    /**
     * creates the user and all parameters are needed
     * */
    @PostMapping(value="/user")
    public ResponseEntity<UserOutputDTO> createUser(@RequestBody @NonNull UserDTO userDTO) throws EmailExistException,ValidatorErrorException,EmailNotFoundException {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    /**
     * requires an object with email and password
     * */
    @PostMapping(value="/user/login")
    public ResponseEntity<TokenDTO> isAuthenticated(@RequestBody @NonNull LoginDTO loginDTO)  throws EmailNotFoundException {

        return ResponseEntity.ok(userService.signIn(loginDTO));
    }

   /**
    * requires
    *  an authorization header
    *  the format is
    *  key : Authorization
    *  value: Bearer {jwt-token}
    * **/
    @GetMapping(value="/test")
    @PreAuthorize("hasAnyAuthority('user:read')")
    public String test(){
        return "hola";
    }


}