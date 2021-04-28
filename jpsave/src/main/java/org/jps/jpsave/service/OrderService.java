package org.jps.jpsave.service;

import org.jps.jpsave.dto.OrderRequest;
import org.jps.jpsave.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.jps.jpsave.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    List<Order> orders = new ArrayList<>();

    @Autowired
    private GouvGeoService gouvGeoService;

    private static int increment = 0;

    public void addOrder(String orderName) {
        Order order = new Order(increment++, orderName);
        orders.add(order);
    }

    public Order addOrderWithCity(OrderRequest orderRequest) throws NotFoundException, IllegalArgumentException {
        Order order = null;

        if (orderRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Order name have to be filled");
        } else {
            order = new Order(increment++, orderRequest.getName(), gouvGeoService.findCity(orderRequest.getLat(), orderRequest.getLon()));
            orders.add(order);
        }
        return order;
    }

    public Order getOrderById(int id) throws NotFoundException {
        return orders.stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Order " + id + " not found"));
    }

    public Order getOrderByName(String name) throws NotFoundException {
        return orders.stream()
                .filter(order -> order.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Order " + name + " not found"));
    }

    public void setOrder(OrderRequest orderReq) throws NotFoundException {
        Order foundOrder = getOrderById(orderReq.getId());
        if (foundOrder != null) {
            foundOrder.setName(orderReq.getName());
            foundOrder.setCity(gouvGeoService.findCity(orderReq.getLat(), orderReq.getLon()));
        } else {
            throw new NotFoundException("Order " + orderReq.getId() + " not found");
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void deleteOrderById(int id) throws NotFoundException {
        if (!orders.remove(getOrderById(id))) {
            throw new NotFoundException("Order " + id + " not found");
        }
    }

}
