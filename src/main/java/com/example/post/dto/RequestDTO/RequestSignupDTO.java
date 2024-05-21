package com.example.post.dto.RequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestSignupDTO {
    private String email;
    private String password;
    private String nickname;
}
