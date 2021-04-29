package org.jps.jpsave.dao;

import org.jps.jpsave.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
