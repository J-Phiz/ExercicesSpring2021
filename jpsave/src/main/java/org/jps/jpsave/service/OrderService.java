package org.jps.jpsave.service;

import org.jps.jpsave.entity.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    List<Order> orders = new ArrayList<>();

    private static int increment = 0;

    public Order addOrder(String orderName) {
        Order order = new Order(increment++, orderName);
        orders.add(order);
        return order;
    }

    public Order getOrderById(int id) {
        return orders.stream().filter(order -> order.getId() == id).findFirst().orElse(null);
    }

    public Order getOrderByName(String name) {
        return orders.stream().filter(order -> order.getName().equals(name)).findFirst().orElse(null);
    }

    public boolean setOrder(Order order) {
        boolean res = false;

        Order foundOrder = getOrderById(order.getId());
        if (foundOrder != null) {
            foundOrder.setName(order.getName());
            res = true;
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
