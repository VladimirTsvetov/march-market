package ru.geekbrains.march.market.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.geekbrains.march.market.auth.servicies.UserService;

import java.util.*;
import java.util.stream.Collectors;

class JwtTokenUtilTest extends SpringBootTestBase{

    @Autowired
    UserService userService;
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;

    @Test
    void generateToken() {

        //Готвим токен из нашего проверенного юзера
        //потом меняем параметры генерации и смотрим, чтобы новый токен не совпал

        UserDetails userDetails = userService.loadUserByUsername("Bob");

        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime);
        String userBobToken =  Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        //Все тоже самое, но меняем Бобу секрет
        String badSecret = "123456678gfgfgfgfgfg";

        String userNonBobToken =  Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, badSecret)
                .compact();

        Assertions.assertNotEquals(userBobToken,userNonBobToken);

    }
}