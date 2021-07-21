package com.bci.msuser.service

import com.bci.msuser.constant.SecurityConstant
import com.bci.msuser.constant.UserServiceTestConstant
import com.bci.msuser.dto.LoginDTO
import com.bci.msuser.dto.TokenDTO
import com.bci.msuser.dto.UserDTO
import com.bci.msuser.dto.UserOutputDTO
import com.bci.msuser.filter.JWTTokenProvider
import com.bci.msuser.model.UserModel
import com.bci.msuser.repository.UserRepository
import com.bci.msuser.util.Validators
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.AuthenticationManager
import spock.lang.Specification


import static com.bci.msuser.constant.UserServiceTestConstant.*;
@SpringBootTest
class UserServiceSpec extends Specification {


    UserService userService

   UserRepository userRepository = Stub(UserRepository)

    JWTTokenProvider jwtTokenProvider

    Validators validators

    AuthenticationManager authenticationManager

    UserServiceTestConstant userServiceTestConstant = Mock(UserServiceTestConstant)

    void setup(){
        this.userService = new UserServiceImpl()
        this.userService.userRepository = userRepository
        this.userService.validators = new Validators()
        this.userService.jwtTokenProvider = new JWTTokenProvider()



    }


    def "createUser"(){
        given:
            UserOutputDTO outputDTO = UserOutputDTO.builder().build()
            //userService.createUser(_) >>    outputDTO
        when:

            def user = UserDTO.builder().
                    name("juan").
                    email("j@gmail.com").
                    password("Basuraa1314").
                    phones(PHONE_ARRAY).build()
            user.phones.add(PHONE_OBJECT)
            UserOutputDTO response = userService.createUser(user)

            System.out.println(response.getId())

        then:
            outputDTO.getId()!= response.getId()
    }

    def "signIn" (){

        given:
            LoginDTO loginDTO = LoginDTO.builder().email("juan@rodriguez.com").password("Basuraa13").build()
            userService.signIn(_)>> TokenDTO.builder().build()
        when:
            def response = userService.signIn(loginDTO)
            System.out.println(response.getAccessToken())
        then:
            def token =TokenDTO.builder().build()
            token.getAccessToken()!= response.getAccessToken()
    }


    def "find user by email"(){
        given: "Creates a constant with an existing email"
            def constant ="juan@rodriguez.com"

            UserModel userModel= UserModel.builder().email(constant).build()
            userRepository.findByEmail(_) >>  userModel
        when: "Calling the method findUserByEmail"
            UserModel response= userService.findUserByEmail(constant)
        then:"Comparing the response result with the constant"
            constant==response.getEmail()
    }
}
