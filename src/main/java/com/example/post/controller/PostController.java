package com.example.post.controller;

import com.example.post.dto.PostDTO;
import com.example.post.dto.RequestDTO.AddRequestPostDTO;
import com.example.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> savePost(@RequestBody AddRequestPostDTO postDTO, Principal user){
        boolean result = postService.savePost(postDTO, user.getName());
        if(result){
            return ResponseEntity.status(HttpStatus.CREATED).body("게실글 추가 완료");
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게실글 추가 실패");
        }
    }

    @GetMapping
    public ResponseEntity<List<PostDTO>> findAllPost(){
        List<PostDTO> postDTOList = postService.findAllPost();
        return ResponseEntity.status(HttpStatus.OK).body(postDTOList);
    }

    @GetMapping("/{postNo}")
    public ResponseEntity<PostDTO> findPost(@PathVariable int postNo){
        PostDTO postDTO = postService.findPost(postNo);
        return ResponseEntity.status(HttpStatus.OK).body(postDTO);
    }
    @DeleteMapping("/{postNo}")
    public ResponseEntity<Void> deletePost(@PathVariable int postNo){
        postService.deletePost(postNo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping("/{postNo}")
    public ResponseEntity<String> updatePost(@PathVariable int postNo,
                                           @RequestBody AddRequestPostDTO postDTO, Principal user){
        boolean result = postService.updatePost(postNo, postDTO, user.getName());
        if(result){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
