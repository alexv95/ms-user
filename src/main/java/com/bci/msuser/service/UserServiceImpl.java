package com.bci.msuser.service;

import com.bci.msuser.dto.LoginDTO;
import com.bci.msuser.dto.UserDTO;

import com.bci.msuser.exception.ExceptionHandling;
import com.bci.msuser.exception.customerrors.EmailNotFoundException;
import com.bci.msuser.exception.customerrors.ValidatorErrorException;
import com.bci.msuser.model.UserModel;
import com.bci.msuser.repository.UserRepository;
import com.bci.msuser.security.UserPrincipal;
import com.bci.msuser.util.RandomUUIDGeneration;
import com.bci.msuser.util.Validators;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;



@Slf4j
@Service("userServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private  UserRepository userRepository;
    private final ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RandomUUIDGeneration customUUID = new RandomUUIDGeneration();
    private Validators validators = new Validators();

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository;
        this.modelMapper=modelMapper;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String email) {
        UserModel user = userRepository.findByEmail(email);
        if (user == null) {

            throw new Error(email);
        } else {
            user.setLastLogin(Instant.now());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);

            return userPrincipal;
        }
    }

    @Override
    public boolean createUser(UserDTO userDTO){
        try{
            if(validators.mailValidator(userDTO.getEmail()) && validators.passwordValidator(userDTO.getPassword())){
                UserModel user = new UserModel();
                user=modelMapper.map(userDTO,UserModel.class);
                user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
                user.setId(customUUID.generateUUID());
                user.setCreatedAt(Instant.now());
                user.setLastLogin(Instant.now());
                user.setIsActive(true);
                user.setModifiedAt(Instant.now());
                userRepository.save(user);
                return true;
            }
            return false;
        }
        catch(Exception e){
            System.out.println(e.toString());
            return false;
        }
    }

    @Override
    public boolean signIn(LoginDTO loginDTO) throws  EmailNotFoundException ,ValidatorErrorException{
        try{
            UserModel findUserByEmail = userRepository.findByEmail(loginDTO.getEmail());
            if(findUserByEmail!=null){

                if(bCryptPasswordEncoder.matches(loginDTO.getPassword(), findUserByEmail.getPassword())){
                    if(findUserByEmail.getIsActive()){{
                        return true;
                    }

                    }
                   throw new ValidatorErrorException("Incorrect password");

                }
            }
            throw new EmailNotFoundException("The username :"+ loginDTO.getEmail() +"is not found");
        }
        catch(Exception e){
            System.out.println(e.toString());
            return false;
        }
    }

    @Override
    public UserModel findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
