package com.bci.msuser;


import com.bci.msuser.dto.LoginDTO;
import com.bci.msuser.dto.TokenDTO;
import com.bci.msuser.dto.UserDTO;
import com.bci.msuser.exception.customerrors.EmailNotFoundException;
import com.bci.msuser.exception.customerrors.ValidatorErrorException;
import com.bci.msuser.model.UserModel;

public interface UserService {



   UserModel findUserByEmail(String email);
   boolean createUser(UserDTO userDTO);


   TokenDTO signIn(LoginDTO loginDTO) throws  EmailNotFoundException , ValidatorErrorException ;

   void tokenValidation(String username,String token );
}
