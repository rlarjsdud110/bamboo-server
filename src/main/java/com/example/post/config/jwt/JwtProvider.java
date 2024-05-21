package com.example.post.config.jwt;


import com.example.post.dto.TokenDTO;
import com.example.post.entity.TokenEntity;
import com.example.post.entity.UserEntity;
import com.example.post.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class JwtProvider {
    private final JwtProperties jwtProperties;
    private final TokenRepository tokenRepository;

    private final Long REFRESH_TOKEN_DURATION = 1000L*60*60*24*3;
    private final Long ACCESS_TOKEN_DURATION = 1000L*60*60*3;

    public TokenDTO createToken(UserEntity userEntity){
        log.info("[JwtProvider] createToken");

        String accessToken = makeToken(ACCESS_TOKEN_DURATION, userEntity);
        String refreshToken = makeToken(REFRESH_TOKEN_DURATION, userEntity);

        tokenRepository.save(TokenEntity.builder()
                .userNo(userEntity.getUserNo())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiration(REFRESH_TOKEN_DURATION)
                .build());

        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String makeToken(Long expire, UserEntity userEntity){
        Date nowDate = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(nowDate)
                .setExpiration(new Date(nowDate.getTime() + expire))
                .setSubject(userEntity.getEmail())
                .claim("id", userEntity.getUserNo())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    public boolean validToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
                new User(claims.getSubject(), "", authorities), token, authorities);
    }
    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
    public Integer getUserNo(String token){
        Claims claims = getClaims(token);
        return claims.get("id", Integer.class);
    }
}
