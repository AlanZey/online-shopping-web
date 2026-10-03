package com.example.ecommerce.controller;
import com.example.ecommerce.common.Result;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.repository.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired private CartService cartService;

    @PostMapping
    public Result<String> add(@RequestBody Map<String, Object> body, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        cartService.addToCart(userId, Long.valueOf(body.get("productId").toString()), Integer.valueOf(body.get("quantity").toString()));
        return Result.success("已加入购物车");
    }

    @GetMapping
    public Result<List<Cart>> list(HttpServletRequest request) {
        return Result.success(cartService.getCartList((Long) request.getAttribute("userId")));
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id, HttpServletRequest request) {
        cartService.deleteCartItem((Long) request.getAttribute("userId"), id);
        return Result.success("已删除");
    }
}