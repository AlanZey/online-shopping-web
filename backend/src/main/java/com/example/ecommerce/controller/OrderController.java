package com.example.ecommerce.controller;
import com.example.ecommerce.common.Result;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.repository.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired private OrderService orderService;

    @PostMapping
    public Result<Long> create(HttpServletRequest request) {
        return Result.success(orderService.createOrder((Long) request.getAttribute("userId")));
    }

    @GetMapping
    public Result<List<Order>> list(HttpServletRequest request) {
        return Result.success(orderService.getUserOrders((Long) request.getAttribute("userId")));
    }
}