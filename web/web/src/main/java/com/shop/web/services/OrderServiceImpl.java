package com.shop.web.services;


import com.shop.web.dto.BookDto;
import com.shop.web.dto.CartDto;
import com.shop.web.dto.OrderDto;
import com.shop.web.mapper.OrderMapper;
import com.shop.web.models.Book;
import com.shop.web.models.Cart;
import com.shop.web.models.Order;
import com.shop.web.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import static com.shop.web.mapper.OrderMapper.mapToOrderDto;
@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private  CartService cartService;
    private  BookService bookService;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CartService cartService,BookService bookService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.bookService=bookService;
    }
    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper::mapToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId).get();
        return mapToOrderDto(order);
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {

        Order order = OrderMapper.mapToOrder(orderDto);
        Cart cart = cartService.getCartById(orderDto.getCartId());

        if (cart == null) {
            throw new IllegalArgumentException("Invalid cartId: " + orderDto.getCartId());
        }
        order.setCart(cart);
        Order savedOrder = orderRepository.save(order);

        for (Book book : cart.getBooks()) {
            bookService.decreaseBookQuantity(book.getId(), 1);
        }

        return OrderMapper.mapToOrderDto(savedOrder);
    }

    @Override
    public OrderDto createOrderForCurrentUser(String currentUserName,String address) {
        CartDto currentCart = cartService.getCartByUserName(currentUserName);
        if (currentCart == null || currentCart.getBooks() == null || currentCart.getBooks().isEmpty()) {
            throw new IllegalStateException("Cart is empty or invalid for the current user");
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderDate(LocalDateTime.now());
        orderDto.setTotalPrice(cartService.calculateTotalPrice(currentCart));
        orderDto.setCartId(currentCart.getId());
        orderDto.setAddress(address);


        return createOrder(orderDto);
    }


}
