package org.jps.jpsave.service;

import org.jps.jpsave.dto.OrderRequest;
import org.jps.jpsave.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Order addOrderWithCity(OrderRequest orderRequest) {
        Order order = new Order(increment++, orderRequest.getName(), gouvGeoService.findCity(orderRequest.getLat(), orderRequest.getLon()));
        orders.add(order);
        return order;
    }

    public Order getOrderById(int id) {
        return orders.stream().filter(order -> order.getId() == id).findFirst().orElse(null);
    }

    public Order getOrderByName(String name) {
        return orders.stream().filter(order -> order.getName().equals(name)).findFirst().orElse(null);
    }

    public Order setOrder(OrderRequest orderReq) {
        Order res = null;

        Order foundOrder = getOrderById(orderReq.getId());
        if (foundOrder != null) {
            foundOrder.setName(orderReq.getName());
            foundOrder.setCity(gouvGeoService.findCity(orderReq.getLat(), orderReq.getLon()));
            res = foundOrder;
        }

        return res;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public boolean deleteOrderById(int id) {
        return orders.remove(getOrderById(id));
    }

}
