package ru.geekbrains.march.market.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.geekbrains.march.market.api.JwtRequest;
import ru.geekbrains.march.market.auth.servicies.UserService;
import ru.geekbrains.march.market.auth.utils.JwtTokenUtil;
import ru.geekbrains.march.market.auth.utils.SpringBootTestBase;

import static org.junit.jupiter.api.Assertions.*;
@RequiredArgsConstructor
class AuthControllerTest extends SpringBootTestBase {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final WebTestClient webTestClient;

    @Test
    void createAuthToken() {

        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername("bob");
        jwtRequest.setPassword("100");

        JwtRequest jwtByHttp = webTestClient.post()
                .uri("/auth/" )
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtRequest.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertEquals(jwtRequest.getUsername(),jwtByHttp.getUsername());
        Assertions.assertEquals(jwtRequest.getPassword(),jwtByHttp.getPassword());
    }
}