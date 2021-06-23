
package com.bci.msuser.controller;

import com.bci.msuser.dto.LoginDTO;
import com.bci.msuser.dto.UserDTO;
import com.bci.msuser.exception.customerrors.EmailNotFoundException;
import com.bci.msuser.exception.customerrors.ValidatorErrorException;
import com.bci.msuser.model.UserModel;
import com.bci.msuser.security.JWTTokenProvider;
import com.bci.msuser.security.UserPrincipal;
import com.bci.msuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import static com.bci.msuser.constant.SecurityConstant.JWT_TOKEN_HEADER;


@RestController
public class UserController{

    private final UserService userService;

    private JWTTokenProvider jwtTokenProvider = new JWTTokenProvider();


    public UserController(@Qualifier(value= "userServiceImpl") UserService userService){
        this.userService=userService;

    }

    @PostMapping(value="/user")
    public ResponseEntity<Boolean> createUser(@RequestBody UserDTO userDTO) throws Exception{
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @PostMapping(value="/user/login")
    public ResponseEntity<Boolean> isAuthenticated(@RequestBody LoginDTO loginDTO)  throws EmailNotFoundException, ValidatorErrorException {

        UserModel user = userService.findUserByEmail(loginDTO.getEmail());
        UserPrincipal userPrincipal= new UserPrincipal(user);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);

        return ResponseEntity.ok(userService.signIn(loginDTO));
    }



    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        System.out.println(jwtTokenProvider.generateJwtToken(user));
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));

        return headers;
    }


}