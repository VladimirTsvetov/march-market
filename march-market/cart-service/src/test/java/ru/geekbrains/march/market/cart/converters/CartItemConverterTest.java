package ru.geekbrains.march.market.cart.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.api.CartItemDto;
import ru.geekbrains.march.market.api.ProductDto;
import ru.geekbrains.march.market.cart.services.CartService;
import ru.geekbrains.march.market.cart.utils.Cart;
import ru.geekbrains.march.market.cart.utils.CartItem;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CartItemConverterTest {

    @Autowired
    CartItemConverter cartItemConverter;


    @Test
    void entityToDto() {


        ProductDto productDto = new ProductDto();
        productDto.setTitle("Хлебб");
        productDto.setPrice(BigDecimal.valueOf(10));
        productDto.setCategoryTitle("Хлеб");
        productDto.setId(111L);

        Assertions.assertNotNull(productDto);

        CartItem cartItem = new CartItem(
                productDto.getId(),
                productDto.getTitle(),
                1,
                productDto.getPrice(),
                productDto.getPrice());
        Assertions.assertNotNull(cartItem);

        CartItemDto cartItemDto = new CartItemDto(cartItem.getProductId(),
                cartItem.getProductTitle(),
                cartItem.getQuantity(), cartItem.getPricePerProduct(),
                cartItem.getPrice());

        Assertions.assertNotNull(cartItemDto);
        Assertions.assertEquals(cartItem.getProductTitle(),cartItemDto.getProductTitle());
        Assertions.assertEquals(cartItem.getProductId(),cartItemDto.getProductId());
        Assertions.assertEquals(cartItem.getQuantity(),cartItemDto.getQuantity());
        Assertions.assertEquals(cartItem.getPricePerProduct(),cartItemDto.getPricePerProduct());
        Assertions.assertEquals(cartItem.getPrice(),cartItemDto.getPrice());

    }
}