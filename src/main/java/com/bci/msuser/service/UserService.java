package com.bci.msuser.service;


import com.bci.msuser.dto.LoginDTO;
import com.bci.msuser.dto.UserDTO;
import com.bci.msuser.exception.customerrors.EmailNotFoundException;
import com.bci.msuser.exception.customerrors.ValidatorErrorException;
import com.bci.msuser.model.UserModel;

public interface UserService {



   UserModel findUserByEmail(String email);
   boolean createUser(UserDTO userDTO);


   boolean signIn(LoginDTO loginDTO) throws  EmailNotFoundException , ValidatorErrorException ;


}
