package com.example.post.dto.RequestDTO;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddRequestPostDTO {
    private String title;
    private String content;
    private String writer;
    private String updated_at;
    private String password;
}
