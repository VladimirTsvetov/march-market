package ru.geekbrains.march.market.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.march.market.core.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
