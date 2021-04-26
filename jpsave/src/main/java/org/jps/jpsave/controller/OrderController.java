package org.jps.jpsave.controller;

import org.apache.coyote.Response;
import org.jps.jpsave.entity.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        return ResponseEntity.ok(order + " Added");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(
           @PathVariable("id") int id
    ) {
        Order order = new Order(id, "orderNum"+id);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping("/search")
    public ResponseEntity<Order> searchOrders(
            @RequestParam("name") String name
    ) {
        Order order = new Order(1, name);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @PutMapping
    public ResponseEntity<Order> setOrderById(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(
            @PathVariable("id") int id
    ) {
        Order order = new Order(id, "orderNum"+id);
        return ResponseEntity.ok(order + " deleted");
    }

}
