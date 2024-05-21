package com.example.post.service;

import com.example.post.dto.PostDTO;
import com.example.post.dto.RequestDTO.AddRequestPostDTO;
import com.example.post.dto.RequestDTO.RequestSignupDTO;
import com.example.post.entity.PostEntity;
import com.example.post.entity.UserEntity;
import com.example.post.exception.CustomException;
import com.example.post.repository.PostRepository;
import com.example.post.repository.UserRepository;
import com.example.post.util.Convert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Convert convert;
    public boolean savePost(AddRequestPostDTO postDTO, String user){
        log.info("[PostService] savePost");
        UserEntity userEntity = userRepository.findByEmail(user)
                .orElseThrow(() -> new IllegalArgumentException("Not Found User Email"));
        System.out.println(postDTO.getPassword());
        if(userEntity.getPassword().equals(postDTO.getPassword())){
            PostEntity postEntity = convert.toPostEntity(postDTO);
            postRepository.save(postEntity);
            return true;
        }
        return false;
    }

    public List<PostDTO> findAllPost(){
        log.info("[PostService] findAllPost");
        List<PostEntity> postEntityList = postRepository.findAll();
        List<PostDTO> postDTOList = new ArrayList<>();
        for (int i = 0; i <postEntityList.size(); i++){
            PostEntity postEntity = postEntityList.get(i);
            postDTOList.add(convert.toPostDTO(postEntity));
        }

        return postDTOList;
    }

    public PostDTO findPost(int postNo){
        PostEntity postEntity = postRepository.findById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("Not Found PostNo"));

        postEntity.setViews(postEntity.getViews()+1);
        postRepository.save(postEntity);
        return convert.toPostDTO(postEntity);
    }

    public void deletePost(int postNo){
        postRepository.deleteById(postNo);
    }

    @Transactional
    public Boolean updatePost(int postNo, AddRequestPostDTO postDTO, String user){
        log.info("[PostService] updatePost");

        PostEntity postEntity = postRepository.findById(postNo)
                        .orElseThrow(() -> new IllegalArgumentException("Not Found PostNo"));

        UserEntity userEntity = userRepository.findByEmail(user)
                        .orElseThrow(() -> new IllegalArgumentException("Not Found User Email"));
        System.out.println(postDTO.getPassword());
        if(userEntity.getPassword().equals(postDTO.getPassword())){
            postEntity.setUpdatedAt(LocalDateTime.now().toString());
            postEntity.updateEntity(postDTO);
            postRepository.save(postEntity);

            return true;
        }
        return false;
    }
}
