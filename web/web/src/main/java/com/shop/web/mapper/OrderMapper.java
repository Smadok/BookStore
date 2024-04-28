package com.shop.web.mapper;
import com.shop.web.dto.CartDto;
import com.shop.web.dto.OrderDto;
import com.shop.web.dto.UserDto;
import com.shop.web.models.Cart;
import com.shop.web.models.Order;

import static com.shop.web.mapper.CartMapper.mapToCartDto;
import static com.shop.web.mapper.UserMapper.mapToUserDto;


public class OrderMapper {

    public static OrderDto mapToOrderDto(Order order) {

        return OrderDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .totalPrice(order.getTotalPrice())
                .cartId(order.getCart().getId())
                .address(order.getAddress())
                .build();
    }
    public static Order mapToOrder(OrderDto orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .orderDate(orderDto.getOrderDate())
                .totalPrice(orderDto.getTotalPrice())
                .cart(Cart.builder().id(orderDto.getCartId()).build())
                .address(orderDto.getAddress())
                .build();
    }
}
