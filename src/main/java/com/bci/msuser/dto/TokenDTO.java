package com.bci.msuser.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO implements Serializable {
    private static final long serialVersionUID=-444444444443121232L;

    @ApiModelProperty(value="accessToken", example="JWT accessToken",required = true)
    public String accessToken;
}
