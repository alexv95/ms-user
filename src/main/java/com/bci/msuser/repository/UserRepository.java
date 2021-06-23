package com.bci.msuser.repository;

import com.bci.msuser.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository  extends MongoRepository<UserModel,String> {
    List<UserModel> findAll();

    UserModel findByEmail(String email) ;

}
