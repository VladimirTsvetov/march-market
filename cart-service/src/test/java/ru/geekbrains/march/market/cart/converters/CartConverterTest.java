package ru.geekbrains.march.market.cart.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.cart.utils.Cart;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CartConverterTest {

    @Autowired
    CartItemConverter cartItemConverter;


    @Test
    void entityToDto() {

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
}