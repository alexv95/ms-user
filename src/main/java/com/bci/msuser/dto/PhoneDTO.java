package com.bci.msuser.dto;



import lombok.*;
import java.io.Serializable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO implements Serializable {
    private static final long serialVersionUID=-444444444443122123L;
    private String phone;
    private String cityCode;
    private String countryCode;
}
