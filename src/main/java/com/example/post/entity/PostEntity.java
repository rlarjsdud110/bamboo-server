package com.example.post.entity;

import com.example.post.dto.RequestDTO.AddRequestPostDTO;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post")
public class PostEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_no")
    @Id
    private int postNo;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "writer")
    private String writer;

    @Column(name = "views")
    private int views;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "created_at")
    private String createdAt;

    public void updateEntity(AddRequestPostDTO postDTO){
        this.content = postDTO.getContent();
        this.title = postDTO.getTitle();
    }
}
