package com.bci.msuser.repository;

import com.bci.msuser.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository  extends MongoRepository<UserModel,String> {


    UserModel findByEmail(String email) ;

}
