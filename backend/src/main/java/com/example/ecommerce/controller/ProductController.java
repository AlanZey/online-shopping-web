package com.example.ecommerce.controller;
import com.example.ecommerce.common.Result;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired private ProductRepository productRepository;

    @GetMapping
    public Result<List<Product>> list() { return Result.success(productRepository.findAll()); }

    @GetMapping("/{id}")
    public Result<Product> detail(@PathVariable Long id) { return Result.success(productRepository.findById(id).orElseThrow()); }
}