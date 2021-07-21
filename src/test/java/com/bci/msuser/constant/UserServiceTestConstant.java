package com.bci.msuser.constant;

import com.bci.msuser.dto.LoginDTO;
import com.bci.msuser.dto.PhoneDTO;
import com.bci.msuser.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;



public class UserServiceTestConstant {
    public static String PHONE_NUMBER= "950863396";
    public static String COUNTRY_CODE ="1";
    public static String CITY_CODE="57";

    public static PhoneDTO PHONE_OBJECT= PhoneDTO.builder().
            phone(PHONE_NUMBER).
            countryCode(COUNTRY_CODE).
            cityCode(CITY_CODE).build();

    public static List<PhoneDTO> PHONE_ARRAY= new ArrayList<PhoneDTO>();

    public static final String NAME="Juan Rodriguez";
    public static final String MAIL="juan@rodriguez.org";

    public static final String PASSWORD="Basuraa13";
    public static final String INVALID_PASSWORD="hunter22";
    public static final String INVALID_MAIL="juan@rodriguez";


    void addPhoneToArray(){
        if(PHONE_ARRAY.isEmpty()){
            PHONE_ARRAY.add(PHONE_OBJECT);
        }
    }
    public UserDTO CREATE_VALID_USER(){
        addPhoneToArray();
        return UserDTO.builder()
                .name(NAME)
                .email(MAIL)
                .password(PASSWORD)
                .phones(PHONE_ARRAY)
                .build();
    }

    public UserDTO CREATE_INVALID_USER_INVALID_MAIL(){
        addPhoneToArray();
        return UserDTO.builder()
                .name(NAME)
                .email(INVALID_MAIL)
                .password(PASSWORD)
                .phones(PHONE_ARRAY)
                .build();

    }

    public UserDTO CREATE_INVALID_USER_INVALID_PASSWORD(){
        addPhoneToArray();
        return UserDTO.builder()
                .name(NAME)
                .email(MAIL)
                .password(INVALID_PASSWORD)
                .phones(PHONE_ARRAY)
                .build();

    }
    public LoginDTO LOGIN_INVALID_USER() {

        return LoginDTO.builder().
                email(INVALID_MAIL).
                password(INVALID_PASSWORD).build();
    }

    public LoginDTO LOGIN_VALID_USER(){
        return LoginDTO.builder().email(MAIL).
                password(PASSWORD).
                build();
    }
    public LoginDTO LOGIN_INVALID_PASSWORD(){
        return LoginDTO.builder()
                .email(MAIL)
                .password(INVALID_PASSWORD).
                build();
    }

}
