package org.jps.jpsave.service;

import org.jps.jpsave.dao.OrderRepository;
import org.jps.jpsave.dto.OrderRequest;
import org.jps.jpsave.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.jps.jpsave.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private GouvGeoService gouvGeoService;

    @Autowired
    private OrderRepository orderRepository;

    public Order addOrderWithCity(OrderRequest orderRequest) throws NotFoundException, IllegalArgumentException {
        Order order = null;

        if (orderRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Order name have to be filled");
        } else {
            order = new Order();
            order.setName(orderRequest.getName());
            order.setCity(gouvGeoService.findCity(orderRequest.getLat(), orderRequest.getLon()));
            orderRepository.save(order);
        }
        return order;
    }

    public Order getOrderById(Integer id) throws NotFoundException {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order " + id + " not found"));
    }

    public Order getOrderByName(String name) throws NotFoundException {
        return orderRepository.findByName(name).orElseThrow(() -> new NotFoundException("Order " + name + " not found"));
    }

    public void setOrder(OrderRequest orderReq) throws NotFoundException {
        Order foundOrder = getOrderById(orderReq.getId());
        if (foundOrder != null) {
            foundOrder.setName(orderReq.getName());
            foundOrder.setCity(gouvGeoService.findCity(orderReq.getLat(), orderReq.getLon()));
            orderRepository.save(foundOrder);
        } else {
            throw new NotFoundException("Order " + orderReq.getId() + " not found");
        }
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrderById(Integer id) throws NotFoundException {
        Order foundOrder = getOrderById(id);
        if (foundOrder != null) {
            orderRepository.delete(foundOrder);
        } else {
            throw new NotFoundException("Order " + id + " not found");
        }
    }

}
