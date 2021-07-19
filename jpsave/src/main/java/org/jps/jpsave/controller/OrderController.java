package org.jps.jpsave.controller;

import org.jps.jpsave.dto.OrderRequest;
import org.jps.jpsave.entity.Order;
import org.jps.jpsave.exception.NotFoundException;
import org.jps.jpsave.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Order> addOrderWithCity(@RequestBody OrderRequest orderReq) {
        Order order = orderService.addOrderWithCity(orderReq);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public Order getOrder(@PathVariable("id") int id) throws NotFoundException {
        return orderService.getOrderById(id);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<Order> searchOrders(@RequestParam(value = "name", required = false) String name) throws NotFoundException {
        return orderService.getOrderByName(name);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<Order> listOrders() {
        return orderService.getOrders();
    }


    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void setOrder(@RequestBody OrderRequest orderReq) throws NotFoundException {
        orderService.setOrder(orderReq);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteOrder(@PathVariable("id") int id) throws NotFoundException {
        orderService.deleteOrderById(id);
    }

}
