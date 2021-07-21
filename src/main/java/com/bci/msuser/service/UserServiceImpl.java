package com.bci.msuser;

import com.bci.msuser.dto.LoginDTO;
import com.bci.msuser.dto.TokenDTO;
import com.bci.msuser.dto.UserDTO;

import com.bci.msuser.exception.customerrors.EmailNotFoundException;
import com.bci.msuser.exception.customerrors.ValidatorErrorException;
import com.bci.msuser.model.UserModel;
import com.bci.msuser.repository.UserRepository;
import com.bci.msuser.security.JWTTokenProvider;
import com.bci.msuser.security.UserPrincipal;
import com.bci.msuser.util.RandomUUIDGeneration;
import com.bci.msuser.util.Validators;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
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
import static com.bci.msuser.constant.SecurityConstant.*;

@Service
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
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
    @Autowired
    public UserServiceImpl(UserRepository userRepository,ModelMapper modelMapper){
        this.userRepository= userRepository;
        this.modelMapper= modelMapper;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("El correo : "+ email+ "no se encuentra registrado");
        }
        else {
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
                user.setRole(ROLE_USER.name());
                user.setAuthorities(ROLE_USER.getAuthorities());
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
    public TokenDTO signIn(LoginDTO loginDTO) throws  EmailNotFoundException ,ValidatorErrorException{
        try{
            UserModel findUserByEmail = userRepository.findByEmail(loginDTO.getEmail());

            if(findUserByEmail!=null){
                authenticate(loginDTO.getEmail(), loginDTO.getPassword());
                UserPrincipal userPrincipal= new UserPrincipal(findUserByEmail);
                String accessToken=jwtTokenProvider.generateJwtToken(userPrincipal);
                //HttpHeaders jwtHeader = getJwtHeader(userPrincipal);

                findUserByEmail.setAccessToken(accessToken);
                userRepository.save(findUserByEmail);
                return TokenDTO.builder().accessToken(accessToken).build();
            }
            throw new EmailNotFoundException("The username :"+ loginDTO.getEmail() +"is not found");
        }
        catch(Exception e){
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public void tokenValidation(String username,String token ){
        System.out.println(jwtTokenProvider.isTokenValid(username,token)) ;
        System.out.println(jwtTokenProvider.getSubject(token));

    }

    @Override
    public UserModel findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }


    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
