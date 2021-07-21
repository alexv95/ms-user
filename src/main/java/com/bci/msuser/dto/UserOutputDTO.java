package com.bci.msuser.dto;

import lombok.*;
import java.io.Serializable;
import java.time.Instant;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOutputDTO implements Serializable {
    private static final long serialVersionUID=-444444444433122L;
    private String id;

    private Instant createdAt;

    private Instant modifiedAt;

    private Instant lastLogin;

    private Boolean isActive;

    private String accessToken;

}
