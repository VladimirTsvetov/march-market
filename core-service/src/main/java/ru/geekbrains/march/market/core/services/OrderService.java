package ru.geekbrains.march.market.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.march.market.core.entities.Order;
import ru.geekbrains.march.market.core.repositories.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    //todo
    //реализовать сохранение ордера
    //тут надо обращаться к сервису катр и запрашивать карту? или примо получать данные из формы?
    //проблема: если сохранять в карте юзера, то тогда запрос имеет смысл
    //если в карте юзер не хранится, то как?
    public void addOrder(Order order){

    }
}
