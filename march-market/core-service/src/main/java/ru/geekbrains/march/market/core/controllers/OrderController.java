package ru.geekbrains.march.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.march.market.core.services.OrderService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    // todo
    //реализуем класс контроллера заказов
    private final OrderService orderService;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(@RequestHeader String username) {

        orderService.addOrder(username);
    }
}
