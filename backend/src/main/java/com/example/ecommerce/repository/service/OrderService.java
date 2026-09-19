package com.example.ecommerce.repository.service;
import com.example.ecommerce.entity.*;
import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired private CartRepository cartRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderItemRepository orderItemRepository;

    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(Long userId) {
        List<Cart> cartList = cartRepository.findByUserId(userId);
        if (cartList.isEmpty()) throw new RuntimeException("购物车为空");

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> items = new ArrayList<>();

        for (Cart cart : cartList) {
            Product product = productRepository.findById(cart.getProductId())
                    .orElseThrow(() -> new RuntimeException("商品不存在"));
            
            int updated = productRepository.deductStock(product.getId(), cart.getQuantity());
            if (updated == 0) throw new RuntimeException("商品【" + product.getName() + "】库存不足");

            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setQuantity(cart.getQuantity());
            item.setPrice(product.getPrice());
            items.add(item);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())));
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setTotalPrice(total);
        order.setStatus("pending");
        orderRepository.save(order);

        for (OrderItem item : items) {
            item.setOrderId(order.getId());
            orderItemRepository.save(item);
        }
        cartRepository.deleteByUserId(userId);
        return order.getId();
    }

    public List<Order> getUserOrders(Long userId) { return orderRepository.findByUserIdOrderByCreatedAtDesc(userId); }
}