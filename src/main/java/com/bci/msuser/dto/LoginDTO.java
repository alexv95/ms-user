package com.bci.msuser.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO implements Serializable {
    //@NotBlank(message="el email es obligatorio")

    @NonNull
    @ApiModelProperty(value="email", example="your email:example@example.org",required = true)
    private String email;

    @NonNull
    @ApiModelProperty(value="password", example="your password",required = true)
    private String password;
}
