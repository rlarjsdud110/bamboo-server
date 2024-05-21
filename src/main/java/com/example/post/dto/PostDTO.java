package com.example.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@Builder
public class PostDTO {
    private int postNo;
    private String title;
    private String content;
    private int views;
    private String updatedAt;
    private String createdAt;
    private String writer;
}
