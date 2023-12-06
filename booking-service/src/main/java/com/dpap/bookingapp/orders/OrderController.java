//package com.dpap.bookingapp.orders;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin("http://localhost:4200")
//@RequestMapping("/orders")
//public class OrderController {
//
//    private final OrderRepository orderRepository;
//    private final OrderService orderService;
//
//    public OrderController(OrderRepository orderRepository, OrderService orderService) {
//        this.orderRepository = orderRepository;
//        this.orderService = orderService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<OrderEntity>> getOrders() {
//        return ResponseEntity.ok(orderRepository.findAll());
//    }
//}
