package com.example.post.util;

import com.example.post.dto.PostDTO;
import com.example.post.dto.RequestDTO.AddRequestPostDTO;
import com.example.post.dto.RequestDTO.RequestSignupDTO;
import com.example.post.entity.PostEntity;
import com.example.post.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class Convert {

    public PostDTO toPostDTO(PostEntity postEntity){
        log.info("[Convert] toPostDTO");

        return PostDTO.builder()
                .content(postEntity.getContent())
                .title(postEntity.getTitle())
                .postNo(postEntity.getPostNo())
                .updatedAt(postEntity.getUpdatedAt())
                .views(postEntity.getViews())
                .writer(postEntity.getWriter())
                .build();
    }

    public PostEntity toPostEntity(AddRequestPostDTO postDTO){
        log.info("[Convert] toPostEntity");
        return PostEntity.builder()
                .writer(postDTO.getWriter())
                .content(postDTO.getContent())
                .title(postDTO.getTitle())
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();
    }

    public UserEntity toRequestUserEntity(RequestSignupDTO requestUserDTO){
        log.info("[Convert] toRequestUserEntity");
        return UserEntity.builder()
                .email(requestUserDTO.getEmail())
                .password(requestUserDTO.getPassword())
                .nickname(requestUserDTO.getNickname())
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .build();
    }
}
