package com.bci.msuser.model;


import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.io.Serializable;
import java.time.Instant;
import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document( collection="user")
public class UserModel implements Serializable {
    private static final long serialVersionUID=1L;

    @Indexed(unique=true)
    @Id
    private String id;

    @Field(value="name")
    private String name;

    @Field(value="email")
    private String email;

    @Field(value="password")
    private String password;

    @CreatedDate
    @Field(value="createdAt")
    private Instant createdAt;

    @LastModifiedDate
    @Field(value="modifiedAt")
    private Instant modifiedAt;

    @LastModifiedBy
    private Instant lastLogin;

    @Field(value="isActive")
    private Boolean isActive;

    @Field(value="phones")
    private List<PhoneModel> phones;

    @Field(value="role")
    private String role; //ROLE_USER{ read, edit }, ROLE_ADMIN {delete}

    @Field(value="authorities")
    private String[] authorities;

}
