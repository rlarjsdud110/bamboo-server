package com.example.post.service;

import com.example.post.config.jwt.JwtProvider;
import com.example.post.dto.RequestDTO.RequestSignInDTO;
import com.example.post.dto.TokenDTO;
import com.example.post.entity.UserEntity;
import com.example.post.exception.CustomException;
import com.example.post.repository.UserRepository;
import com.example.post.dto.RequestDTO.RequestSignupDTO;
import com.example.post.util.Convert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final Convert convert;
    private final JwtProvider jwtProvider;

    public void save(RequestSignupDTO requestUserDTO){
        log.info("[UserService] save");
        if(checkUser(requestUserDTO)){
            userRepository.save(convert.toRequestUserEntity(requestUserDTO));
        }
    }

    public TokenDTO login(RequestSignInDTO requestSignInDTO){
        log.info("[UserService] login");
        UserEntity userEntity = userRepository.findByEmail(requestSignInDTO.getEmail())
                .orElseThrow(() -> new CustomException("존재하지 않는 이메일 입니다.", HttpStatus.BAD_REQUEST));

        if (!userEntity.getPassword().equals(requestSignInDTO.getPassword())) {
            throw new CustomException("비밀번호가 달라요", HttpStatus.BAD_REQUEST);
        }

        return jwtProvider.createToken(userEntity);
    }

    public String getNickname(String email){
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Not Found email"));
        return userEntity.getNickname();
    }

    private Boolean checkUser(RequestSignupDTO requestSignupDTO){
        Optional<UserEntity> userEmailEntity = userRepository.findByEmail(requestSignupDTO.getEmail());
        if(userEmailEntity.isPresent()){
            throw new CustomException("이미 존재하는 이메일 입니다.", HttpStatus.BAD_REQUEST);
        }
        Optional<UserEntity> userNicknameEntity = userRepository.findByNickname(requestSignupDTO.getNickname());
        if(userNicknameEntity.isPresent()){
            throw new CustomException("이미 존재하는 닉네임 입니다.", HttpStatus.BAD_REQUEST);
        }
        return true;
    }
}
