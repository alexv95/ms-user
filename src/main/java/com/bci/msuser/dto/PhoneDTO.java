package com.bci.msuser.dto;



import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.io.Serializable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO implements Serializable {
    private static final long serialVersionUID=-444444444443122123L;
    @NonNull
    @ApiModelProperty(value="phone", example="your phone number",required = true)
    private String phone;
    @NonNull
    @ApiModelProperty(value="cityCode", example="your city code ex: 9",required = true)
    private String cityCode;
    @NonNull
    @ApiModelProperty(value="countryCode", example="your country code ex : +56",required = true)
    private String countryCode;
}
