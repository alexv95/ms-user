package com.bci.msuser.service;

import com.bci.msuser.dto.LoginDTO;
import com.bci.msuser.dto.TokenDTO;
import com.bci.msuser.dto.UserDTO;

import com.bci.msuser.dto.UserOutputDTO;
import com.bci.msuser.exception.customerrors.EmailExistException;
import com.bci.msuser.exception.customerrors.EmailNotFoundException;
import com.bci.msuser.exception.customerrors.ValidatorErrorException;
import com.bci.msuser.model.UserModel;
import com.bci.msuser.repository.UserRepository;
import com.bci.msuser.filter.JWTTokenProvider;
import com.bci.msuser.security.UserPrincipal;
import com.bci.msuser.util.RandomUUIDGeneration;
import com.bci.msuser.util.Validators;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.bci.msuser.enumeration.Role.*;
import static com.bci.msuser.constant.UserImplConstant.*;

@Service
@Qualifier("userDetailsService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RandomUUIDGeneration customUUID = new RandomUUIDGeneration();
    private Validators validators = new Validators();
    @Autowired
    private JWTTokenProvider jwtTokenProvider= new JWTTokenProvider();

    @Autowired
    private AuthenticationManager authenticationManager = new AuthenticationManager() {
        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            return null;
        }
    };



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_MAIL);
        }
        else {
            user.setLastLogin(Instant.now());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            return userPrincipal;
        }
    }

    @Override
    public UserOutputDTO createUser(UserDTO userDTO) throws EmailExistException,ValidatorErrorException,EmailNotFoundException {
        if(validators.mailValidator(userDTO.getEmail()) && validators.passwordValidator(userDTO.getPassword())){
            UserModel findUserByEmail = findUserByEmail(userDTO.getEmail());
            if(findUserByEmail==null){
                UserModel user = new UserModel();
                user=modelMapper.map(userDTO,UserModel.class);
                user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
                user.setId(customUUID.generateUUID());
                user.setCreatedAt(Instant.now());
                user.setLastLogin(Instant.now());
                user.setIsActive(true);
                user.setModifiedAt(Instant.now());
                user.setRole(ROLE_USER.name());
                user.setAuthorities(ROLE_USER.getAuthorities());
                userRepository.save(user);
                UserPrincipal userPrincipal= new UserPrincipal(user);
                String accessToken=jwtTokenProvider.generateJwtToken(userPrincipal);
                /*TokenDTO callSignIn=this.signIn(
                        LoginDTO.
                        builder().
                        email(userDTO.getEmail()).
                        password(userDTO.getPassword()).
                        build()
                );*/
                UserOutputDTO userOutputDTO = modelMapper.map(user,UserOutputDTO.class);
                userOutputDTO.setAccessToken(accessToken);
                return userOutputDTO;
            }
            throw new EmailExistException(EMAIL_ALREADY_EXISTS);
        }
        throw new ValidatorErrorException(VALIDATOR_ERROR);
    }

    @Override
    public TokenDTO signIn(LoginDTO loginDTO) throws  EmailNotFoundException {
        UserModel findUserByEmail = findUserByEmail(loginDTO.getEmail());

        if(findUserByEmail!=null){
            authenticate(loginDTO.getEmail(), loginDTO.getPassword());
            UserPrincipal userPrincipal= new UserPrincipal(findUserByEmail);
            String accessToken=jwtTokenProvider.generateJwtToken(userPrincipal);
            findUserByEmail.setLastLogin(Instant.now());
            userRepository.save(findUserByEmail);
            return TokenDTO.builder().accessToken(accessToken).build();
        }
        throw new EmailNotFoundException(NO_USER_FOUND_BY_MAIL);
    }


/**
    public void tokenValidation(String username,String token ){
        System.out.println(jwtTokenProvider.isTokenValid(username,token)) ;
        System.out.println(jwtTokenProvider.getSubject(token));

    }
**/
    @Override
    public UserModel findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

/** method to generate as header
    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }
*/
    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
