package org.jps.jpsave.dao;

import org.jps.jpsave.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    List<Order> findByName(String name);

    @Override
    List<Order> findAll();
}
