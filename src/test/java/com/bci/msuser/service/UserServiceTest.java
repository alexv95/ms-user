package com.bci.msuser.service;


import com.bci.msuser.constant.UserServiceTestConstant;
import com.bci.msuser.dto.LoginDTO;
import com.bci.msuser.dto.TokenDTO;
import com.bci.msuser.dto.UserOutputDTO;
import com.bci.msuser.exception.customerrors.EmailExistException;
import com.bci.msuser.exception.customerrors.EmailNotFoundException;
import com.bci.msuser.exception.customerrors.ValidatorErrorException;
import com.bci.msuser.model.UserModel;
import com.bci.msuser.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.bci.msuser.constant.UserServiceTestConstant.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.test.context.event.annotation.AfterTestClass;

import static com.bci.msuser.constant.UserServiceTestConstant.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {

    @Autowired
    private UserService userService;
    private UserServiceTestConstant userServiceTestConstant= new UserServiceTestConstant();

    @Autowired
    private UserRepository userRepository;


    @Test
    @Order(1)
    void createUser()throws EmailExistException,ValidatorErrorException,EmailNotFoundException
    {
        UserOutputDTO response= userService.createUser(userServiceTestConstant.CREATE_VALID_USER());

        /**
         * the following line compares if response is equal to the output class,
         * it does not verify the integrity of the values
         * */
        Assert.assertEquals(UserOutputDTO.class,response.getClass());
    }

    @Test
    @Order(2)
    void createUserWithExistingData() throws EmailExistException,ValidatorErrorException{

        Assertions.assertThrows(EmailExistException.class, () -> {
            userService.createUser(userServiceTestConstant.CREATE_VALID_USER());
        });

    }
    @Test
    @Order(3)
    void createUserWithInvalidMail() throws EmailExistException,ValidatorErrorException{
        Assertions.assertThrows(ValidatorErrorException.class, () -> {
            userService.createUser(userServiceTestConstant.CREATE_INVALID_USER_INVALID_MAIL());
        });
    }

    @Test
    @Order(4)
    void createUserWithInvalidPassword() throws EmailExistException,ValidatorErrorException{
        Assertions.assertThrows(ValidatorErrorException.class, () -> {
            userService.createUser(userServiceTestConstant.CREATE_INVALID_USER_INVALID_PASSWORD());
        });
    }
    @Test
    @Order(5)
    void findUserByEmail(){
        UserModel response= userService.findUserByEmail(MAIL);
        Assertions.assertEquals(response.getEmail(),MAIL);
    }

    @Test
    @Order(6)
    void findUserByEmailNull(){
        UserModel response= userService.findUserByEmail(INVALID_MAIL);
        Assertions.assertNull(response);
    }

    @Test
    @Order(7)
    void loginSuccess()throws EmailNotFoundException {

        TokenDTO response =userService.signIn(userServiceTestConstant.LOGIN_VALID_USER());
        Assert.assertNotNull(response);
    }

    @Test
    @Order(8)
    void loginFailsInvalidUser()throws EmailNotFoundException{
        Assertions.assertThrows(EmailNotFoundException.class, () -> {
            userService.signIn(userServiceTestConstant.LOGIN_INVALID_USER());
        });

    }

    @Test
    @Order(9)
    void loginFailsIncorrectPassword()throws EmailNotFoundException{
        Assertions.assertThrows(BadCredentialsException.class, () -> {
            userService.signIn(userServiceTestConstant.LOGIN_INVALID_PASSWORD());
        });
    }
    @Test
    @Order(10)
    void loginFailsUserInactive()throws EmailExistException{
        UserModel userModel=userService.findUserByEmail(MAIL);
        userModel.setIsActive(false);
        userRepository.save(userModel);
        Assertions.assertThrows(LockedException.class, () -> {
            userService.signIn(userServiceTestConstant.LOGIN_VALID_USER());
        });

    }
    /**
     * this method is not a test
     * however After and AfterClass annotation are not working
     * could need a fix in the future or a refactor.
     * this method deletes the user used on the previous examples
     * */
    @Test
    @Order(11)
    public final void deleteBaseUser(){
        userRepository.delete(userService.findUserByEmail(MAIL));

    }
}