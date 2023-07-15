package ru.geekbrains.march.market.cart.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.cart.integrations.ProductServiceIntegration;
import ru.geekbrains.march.market.cart.utils.Cart;
import ru.geekbrains.march.market.cart.utils.CartItem;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RequiredArgsConstructor

class CartServiceTest {

    private Cart cart;


    @Test
    void addToCart() {
        ProductDto p = new ProductDto();
        ProductDto productDto = new ProductDto();
        productDto.setTitle("Хлебб");
        productDto.setPrice(BigDecimal.valueOf(10));
        productDto.setCategoryTitle("Хлеб");
        productDto.setId(111L);

        Assertions.assertNotNull(productDto);
        cart.clear();
        Assertions.assertEquals(cart.getItems().isEmpty(),true);
        cart.add(p);
        Assertions.assertNotEquals(cart.getItems().isEmpty(),true);
        CartItem savedItem = cart.getItems().get(0);
        Assertions.assertEquals(productDto.getPrice(),savedItem.getPrice());
        Assertions.assertEquals(productDto.getId(),savedItem.getProductId());
        Assertions.assertEquals(productDto.getTitle(),savedItem.getProductTitle());
    }
}