package com.bci.msuser.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID=-444444444433122L;
    @NonNull
    @ApiModelProperty(value="password", example="your name",required = true)
    private String name;
    @NonNull
    @ApiModelProperty(value="email", example="your email ex: example@example.com",required = true)
    private String email;
    @NonNull
    @ApiModelProperty(value="password", example="your password",required = true)
    private String password;
    @NonNull
    @ApiModelProperty(value="phones", example="phone arrays of phones",required = true)
    private List<PhoneDTO> phones;
}
