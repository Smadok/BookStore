package com.shop.web.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private int id;
    private LocalDateTime orderDate;
    private double totalPrice;
    private int cartId;
    private String address;
    private Map<Integer, Integer> bookQuantities;
}

