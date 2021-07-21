package com.bci.msuser.service

import com.bci.msuser.model.UserModel
import com.bci.msuser.repository.UserRepository
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
@SpringBootTest
class UserServiceTest extends Specification {
    def myService=Mock(UserService)

    def "FindUserByEmail"() {
    }
    def "find user by email"(){
        given: "Creates a constant with an existing email"
            def constant ="juan@rodriguez.com"
        when: "Calling the method findUserByEmail"
            UserModel response= myService.findUserByEmail(constant)
        then:"Comparing the response result with the constant"
        constant==response.getEmail()
    }
}
