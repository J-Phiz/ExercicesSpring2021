package org.jps.jpsave.configuration;

import org.jps.jpsave.dao.OrderRepository;
import org.jps.jpsave.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitDbConfiguration {

    @Autowired
    OrderRepository orderRepository;

    @Bean
    public void initialisationDb() {
        Order order = new Order();
        order.setId(1);
        order.setName("Jean-Mi1");
        order.setCity("Tours");
        orderRepository.save(order);

        order.setId(2);
        order.setName("Jean-Mi2");
        order.setCity("Bordeaux");
        orderRepository.save(order);

        order.setId(3);
        order.setName("Jean-Mi3");
        order.setCity("Lyon");
        orderRepository.save(order);
    }

}
