package ru.geekbrains.march.market.cart.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.cart.properties.ProductServiceIntegrationProperties;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CartTest {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    @Test
    void add() {
        ProductDto productDto = new ProductDto();
        productDto.setTitle("Хлебб");
        productDto.setPrice(BigDecimal.valueOf(10));
        productDto.setCategoryTitle("Хлеб");
        productDto.setId(111L);

        Assertions.assertNotNull(productDto);
        items.clear();

        CartItem cartItem = new CartItem(
                productDto.getId(),
                productDto.getTitle(),
                1,
                productDto.getPrice(),
                productDto.getPrice());
        items.add(cartItem);

        CartItem savedItem = items.get(0);
        Assertions.assertEquals(productDto.getPrice(),savedItem.getPrice());
        Assertions.assertEquals(productDto.getId(),savedItem.getProductId());
        Assertions.assertEquals(productDto.getTitle(),savedItem.getProductTitle());


    }

    @Test
    void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;

        Assertions.assertEquals(items.isEmpty(),true);
        Assertions.assertEquals(totalPrice,BigDecimal.ZERO);
    }


}