package com.example.ecommerce.repository.service;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartService {
    @Autowired private CartRepository cartRepository;
    @Autowired private ProductRepository productRepository;

    public void addToCart(Long userId, Long productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("商品不存在"));
        if (product.getStock() < quantity) throw new RuntimeException("库存不足");
        
        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId).orElseGet(() -> {
            Cart c = new Cart(); c.setUserId(userId); c.setProductId(productId); c.setQuantity(0); return c;
        });
        cart.setQuantity(cart.getQuantity() + quantity);
        cartRepository.save(cart);
    }

    public List<Cart> getCartList(Long userId) { return cartRepository.findByUserId(userId); }

    public void deleteCartItem(Long userId, Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("购物车项不存在"));
        if (!cart.getUserId().equals(userId)) throw new RuntimeException("无权操作");
        cartRepository.delete(cart);
    }
}