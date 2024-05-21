package com.example.post.controller;

import com.example.post.dto.RequestDTO.RequestSignInDTO;
import com.example.post.dto.RequestDTO.RequestSignupDTO;
import com.example.post.dto.TokenDTO;
import com.example.post.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody RequestSignupDTO requestUserDTO){
        userService.save(requestUserDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody RequestSignInDTO requestSignInDTO) {
        TokenDTO tokenDTO = userService.login(requestSignInDTO);
        return ResponseEntity.status(HttpStatus.OK).body(tokenDTO);
    }
    @PostMapping("/nickname")
    public ResponseEntity<String> nickname(Principal user){
        String nickname = userService.getNickname(user.getName());
        return ResponseEntity.status(HttpStatus.OK).body(nickname);
    }

}
