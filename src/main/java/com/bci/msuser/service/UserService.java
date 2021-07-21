package com.bci.msuser.service;


import com.bci.msuser.dto.LoginDTO;
import com.bci.msuser.dto.TokenDTO;
import com.bci.msuser.dto.UserDTO;
import com.bci.msuser.dto.UserOutputDTO;
import com.bci.msuser.exception.customerrors.EmailExistException;
import com.bci.msuser.exception.customerrors.EmailNotFoundException;
import com.bci.msuser.exception.customerrors.ValidatorErrorException;
import com.bci.msuser.model.UserModel;

public interface UserService {



   /**
    * returns a User if exists, if not returns null object
    * */
   UserModel findUserByEmail(String email);
   /**
    * returns if valid an UserOutputDTO with some of the registered values of the current user
    * */
   UserOutputDTO createUser(UserDTO userDTO) throws EmailExistException,ValidatorErrorException,EmailNotFoundException;


   /**
    * return a TokenDTO object if valid
    * accessToken : {validToken}
    * */
   TokenDTO signIn(LoginDTO loginDTO) throws  EmailNotFoundException  ;


}
