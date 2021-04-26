package org.jps.jpsave.controller;

import org.jps.jpsave.entity.Order;
import org.jps.jpsave.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody String name) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Order order = orderService.addOrder(name);
        if (order != null) {
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(order);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(
           @PathVariable("id") int id
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Order order = orderService.getOrderById(id);
        if (order != null) {
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(order);
    }

    @GetMapping("/search")
    public ResponseEntity<Order> searchOrders(
            @RequestParam("name") String name
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Order order = orderService.getOrderByName(name);
        if (order != null) {
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(order);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Order>> listOrders() {
        HttpStatus status = HttpStatus.NOT_FOUND;

        List<Order> orders = orderService.getOrders();
        if (orders != null && !orders.isEmpty()) {
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(orders);
    }


    @PutMapping
    public ResponseEntity<Order> setOrder(@RequestBody Order order) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        if (orderService.setOrder(order)) {
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(
            @PathVariable("id") int id
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        if (orderService.deleteOrderById(id)) {
            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body("Order with id " + id);
    }

}
