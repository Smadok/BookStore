package com.shop.web.controllers;
import com.shop.web.dto.OrderDto;
import com.shop.web.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping
    public String getAllOrders(Model model) {
        List<OrderDto> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order-list"; // This will resolve to order-list.html template
    }

    @GetMapping("/{orderId}")
    public String getOrderById(@PathVariable int orderId, Model model) {
        OrderDto orderDto = orderService.getOrderById(orderId);
        if (orderDto != null) {
            model.addAttribute("order", orderDto);
            return "order-details";
        } else {
            return "redirect:/orders";
        }
    }

    @GetMapping("/new")
    public String showOrderForm(Model model) {
        model.addAttribute("orderDto", new OrderDto());
        return "order-create";
    }

    @PostMapping("/new")
    public String createOrder(@ModelAttribute("orderDto") OrderDto orderDto, Model model) {

        return "redirect:/orders/success";
    }
    @PostMapping("/placeOrder")
    public String placeOrder(@ModelAttribute("orderDto") OrderDto orderDto,
                             @RequestParam("address") String address,
                             Model model) {
        String currentUserName = getCurrentUserName();

        OrderDto createdOrder = orderService.createOrderForCurrentUser(currentUserName, address);

        model.addAttribute("createdOrder", createdOrder);

        return "redirect:/orders/success";
    }
    private String getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
