package com.example.ecommerce.controller;
import com.example.ecommerce.common.Result;
import com.example.ecommerce.repository.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private UserService userService;

    @PostMapping("/register")
    public Result<String> register(@RequestBody Map<String, String> body) {
        userService.register(body.get("username"), body.get("password"), body.get("email"), body.get("phone"));
        return Result.success("注册成功");
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody Map<String, String> body) {
        return Result.success(userService.login(body.get("username"), body.get("password")));
    }
}