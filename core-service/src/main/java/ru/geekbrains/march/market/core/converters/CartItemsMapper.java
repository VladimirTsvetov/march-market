package ru.geekbrains.march.market.core.converters;

import ru.geekbrains.march.market.api.CartDto;
import ru.geekbrains.march.market.api.CartItemDto;
import ru.geekbrains.march.market.core.entities.OrderItem;
import ru.geekbrains.march.market.core.entities.Product;
import ru.geekbrains.march.market.core.services.ProductService;

import java.util.ArrayList;
import java.util.List;

public class CartItemsMapper {

    public List<OrderItem> cartItemsToOrderItems(CartDto cartDto, ProductService productService){
        //todo
        //реализуем получение OrderItem-ов из листа CartItem-ов
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        List<CartItemDto> cartItemDtoList = cartDto.getItems();
        for(CartItemDto cartItemDto : cartItemDtoList){
            orderItem.setProduct(productService.findById(cartItemDto.getProductId()).get());
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }
}
