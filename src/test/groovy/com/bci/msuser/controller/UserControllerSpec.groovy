package com.bci.msuser.controller

import com.bci.msuser.dto.PhoneDTO
import com.bci.msuser.dto.UserDTO
import com.bci.msuser.service.UserService
import io.cucumber.messages.internal.com.google.gson.Gson
import org.apache.catalina.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import com.bci.msuser.constant.UserServiceTestConstant;

import static org.springframework.http.HttpStatus.*;
import org.springframework.http.MediaType;

import static com.bci.msuser.constant.UserServiceTestConstant.*;
class UserControllerSpec extends Specification {
    MockMvc mockMvc
    Gson gson
    @Autowired
    UserService userService = Stub(UserService)


    UserServiceTestConstant userServiceTestConstant  = new UserServiceTestConstant()

    def setup(){
        gson = new Gson()
        mockMvc = standaloneSetup(new UserController(userService)).build()

    }


    def "create valid user"(){
        given:
            UserDTO user= userServiceTestConstant.CREATE_VALID_USER()
        when :
            def response = mockMvc.perform(post("/user")
                    .contentType(MediaType.APPLICATION_JSON).
                    content(gson.toJson(user)))
                    .andReturn().response

        then:
        HttpStatus.OK.value() ==response.getStatus()
    }




}
