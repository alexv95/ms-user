package com.bci.msuser.dto;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO implements Serializable {
    private static final long serialVersionUID=-444444444443121232L;
    public String accessToken;
}
