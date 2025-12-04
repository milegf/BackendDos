package com.loopie.backend.modules.order;

import com.loopie.backend.modules.product.Product;
import com.loopie.backend.modules.product.ProductRepository;
import com.loopie.backend.modules.user.User;
import com.loopie.backend.modules.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe el pedido."));
    }

    @Transactional
    public Order createOrder(Long userId, OrderRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No existe el usuario."));

        Order order = Order.builder()
                .user(user)
                .total(request.getTotal())
                .status("PENDING")
                .date(LocalDateTime.now())
                .build();

        List<OrderItem> items = new ArrayList<>();
        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("No existe el producto: " + itemRequest.getProductId()));

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(product.getPrecio())
                    .build();
            items.add(item);
        }

        order.setItems(items);
        return orderRepository.save(order);
    }
}
