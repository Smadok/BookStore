package com.shop.web.services;

import com.shop.web.dto.OrderDto;
import com.shop.web.models.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<OrderDto> getAllOrders();

    OrderDto getOrderById(int orderId);

    OrderDto createOrder(OrderDto orderDto);

    OrderDto createOrderForCurrentUser(String currentUserName,String address);
}
