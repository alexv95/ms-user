package com.bci.msuser.dto;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO implements Serializable {
    private String email;
    private String password;
}
