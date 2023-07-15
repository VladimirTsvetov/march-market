package ru.geekbrains.march.market.core.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.geekbrains.march.market.core.SpringBootTestBase;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.testcontainers.shaded.com.google.common.net.HttpHeaders.ACCEPT;

class OrderControllerTest extends SpringBootTestBase {
    @Autowired
    WebTestClient webTestClient;


    @Test
    void createNewOrder() {
        var newUserName = "Bob1";

        var userCreationResponse = webTestClient
                .post()
                .uri("/order")
                .body(Mono.just(newUserName), String.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(CREATED)
                .returnResult(Void.class);
    }
}