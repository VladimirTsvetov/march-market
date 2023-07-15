package ru.geekbrains.march.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.core.converters.CartItemsMapper;
import ru.geekbrains.march.market.core.entities.Order;
import ru.geekbrains.march.market.core.entities.OrderItem;
import ru.geekbrains.march.market.core.entities.User;
import ru.geekbrains.march.market.core.repositories.OrderRepository;
import ru.geekbrains.march.market.core.services.OrderService;
import ru.geekbrains.march.market.core.services.ProductService;
import ru.geekbrains.march.market.core.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    // todo
    //реализуем класс контроллера заказов
    private final OrderService orderService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(@RequestBody CartDto cartDto,
                               @RequestParam Principal principal) {
        Order order = new Order();
        User user = userService.findByUsername(principal.getName()).get();
        CartItemsMapper mapper = new CartItemsMapper();
        List<OrderItem> orderItemList = mapper.cartItemsToOrderItems(cartDto,productService);


        order.setItems(orderItemList);
        order.setUser(user);
        order.setTotalPrice(cartDto.getTotalPrice());
        orderService.addOrder(order);
    }
}
