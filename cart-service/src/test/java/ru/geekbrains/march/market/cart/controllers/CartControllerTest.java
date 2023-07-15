package ru.geekbrains.march.market.cart.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.cart.SpringBootTestBase;
import ru.geekbrains.march.market.cart.converters.CartItemConverter;
import ru.geekbrains.march.market.cart.utils.Cart;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.apache.tomcat.util.http.fileupload.FileUploadBase.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
/*
 @GetMapping
    public CartDto getCurrentCart() {
        return cartConverter.entityToDto(cartService.getCurrentCart());
    }
 */

class CartControllerTest extends SpringBootTestBase {
    @Autowired
    CartItemConverter cartItemConverter;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void getCurrentCart() {
        Cart cart = new Cart();

        ProductDto productDto = new ProductDto();
        productDto.setTitle("Хлебб");
        productDto.setPrice(BigDecimal.valueOf(10));
        productDto.setCategoryTitle("Хлеб");
        productDto.setId(111L);

        Assertions.assertNotNull(productDto);

        cart.add(productDto);
        Assertions.assertNotEquals(cart.getItems().isEmpty(),true);
        CartDto cartDto = new CartDto(cart.getItems()
                .stream()
                .map(cartItemConverter::entityToDto)
                .collect(Collectors.toList()),
                cart.getTotalPrice());

        Assertions.assertNotNull(cartDto);
        Assertions.assertEquals(cartDto.getTotalPrice(),cart.getTotalPrice());
        Assertions.assertEquals(cartDto.getItems(),cart.getItems());


    }

    @Test
    void addProductToCart() {

        webTestClient
                .get()
                .uri("/api/users/{id}", 1)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(OK);
    }
}