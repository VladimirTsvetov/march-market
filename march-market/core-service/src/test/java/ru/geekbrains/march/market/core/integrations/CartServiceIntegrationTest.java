package ru.geekbrains.march.market.core.integrations;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.core.SpringBootTestBase;

import static org.junit.jupiter.api.Assertions.*;

@Component
@RequiredArgsConstructor
class CartServiceIntegrationTest  extends SpringBootTestBase {
    private final WebTestClient webTestClient;

    @Test
    void getCurrentCart() {
        CartDto cartDto = webTestClient.get()
                .uri("/cart/" )
                .exchange()
                .expectStatus().isOk()
                .expectBody(CartDto.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(cartDto);
        Assertions.assertNotNull(cartDto.getTotalPrice());
        Assertions.assertNotNull(cartDto.getItems());
    }

}