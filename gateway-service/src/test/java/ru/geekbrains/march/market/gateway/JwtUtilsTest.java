package ru.geekbrains.march.market.gateway;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.geekbrains.march.market.auth.servicies.UserService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



class JwtUtilsTest extends SpringBootTestBase{
    @Autowired
    UserService userService;
    @Test
    void getAllClaimsFromToken() {

        //Готвим токен из нашего проверенного юзера
        //потом меняем параметры генерации и смотрим, чтобы новый токен не совпал
        String secret = "h4f8093h4f983yhrt9834hr0934hf0hf493g493gf438rh438th34g34g";
        Integer jwtLifetime = 123456;

        UserDetails userDetails = userService.loadUserByUsername("Bob");

        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime);
        String token =  Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();


        Claims newClaims =  Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJwt(token)
                .getBody();

        //подменим секрет и посмотрим, как оно выкрутится
        Claims badClaims =  Jwts.parser()
                .setSigningKey("123456789")
                .parseClaimsJwt(token)
                .getBody();

        Assertions.assertNotNull(newClaims);
        Assertions.assertNotNull(badClaims);
        Assertions.assertNotNull(newClaims.getSubject());
        Assertions.assertNotNull(badClaims.getSubject());
        Assertions.assertEquals(newClaims.getSubject(),badClaims.getSubject());

    }

}