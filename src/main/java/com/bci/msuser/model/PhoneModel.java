package com.bci.msuser.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneModel implements Serializable {
    @Field(value="phone")
    private String phone;

    @Field(value="cityCode")
    private String cityCode;

    @Field(value="countryCode")
    private String countryCode;

}
