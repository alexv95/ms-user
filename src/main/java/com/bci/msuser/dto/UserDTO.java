package com.bci.msuser.dto;


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
    private String name;
    private String email;
    private String password;
    private List<PhoneDTO> phones;
}
