package ru.geekbrains.march.market.core.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.core.SpringBootTestBase;
import ru.geekbrains.march.market.core.entities.Order;
import ru.geekbrains.march.market.core.entities.OrderItem;
import ru.geekbrains.march.market.core.integrations.CartServiceIntegration;
import ru.geekbrains.march.market.core.repositories.OrderRepository;
import ru.geekbrains.march.market.core.repositories.ProductRepository;

import javax.transaction.Transactional;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RequiredArgsConstructor
class OrderServiceTest extends SpringBootTestBase {

    private final OrderRepository orderRepository;
    //private final ProductRepository productRepository;
    private final ProductService productService;
    private final CartServiceIntegration cartServiceIntegration;


    @Test
    @Transactional
    void addOrder() {
        CartDto cartDto = cartServiceIntegration.getCurrentCart();
        Order order = new Order();

        Assertions.assertNotNull(order);  //проверяем, что не null

        order.setUsername("Bob");
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setItems(cartDto.getItems().stream().map(cartItem ->
                new OrderItem(
                        productService.findById(cartItem.getProductId()).get(),
                        order,
                        cartItem.getQuantity(),
                        cartItem.getPricePerProduct(),
                        cartItem.getPrice()
                )
        ).collect(Collectors.toList()));
        orderRepository.save(order);

        //теперь будем сверять бумажки
        Order savedOrder = orderRepository.getById(order.getId());

        Assertions.assertNotNull(savedOrder);
        Assertions.assertNotNull(savedOrder.getId());
        Assertions.assertEquals(order.getId(),savedOrder.getId());
        Assertions.assertEquals(order.getUsername(),savedOrder.getUsername());
        Assertions.assertEquals(order.getTotalPrice(),savedOrder.getTotalPrice());

        //cartServiceIntegration.clear();
    }
}