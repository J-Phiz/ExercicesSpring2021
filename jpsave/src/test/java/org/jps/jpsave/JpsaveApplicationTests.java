package org.jps.jpsave;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jps.jpsave.entity.Order;
import org.jps.jpsave.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class JpsaveApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private OrderService orderService;

    @AfterEach
    void afterTests() {
        List<Integer> ids = orderService.getOrders().stream().map(Order::getId).collect(Collectors.toList());
        ids.forEach(id -> orderService.deleteOrderById(id));
    }

    @Test
    void getOrder() throws Exception {
        orderService.addOrder("bob");

        mvc.perform(
                get("/order/" + orderService.getOrders().get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("bob")));
    }

    @Test
    void getOrders() throws Exception {
        orderService.addOrder("bob");
        orderService.addOrder("bob2");

        mvc.perform(get("/order/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(orderService.getOrders().get(0).getId())))
                .andExpect(jsonPath("$[0].name", is("bob")))
                .andExpect(jsonPath("$[1].id", is(orderService.getOrders().get(1).getId())))
                .andExpect(jsonPath("$[1].name", is("bob2")));
    }

    @Test
    void createOrder() throws Exception {
        Order order = new Order();
        order.setName("bob");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson=ow.writeValueAsString(order);

        mvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(orderService.getOrders().get(0).getId())))
                .andExpect(jsonPath("$.name", is("bob")));
    }

    @Test
    void putOrders() throws Exception {
        orderService.addOrder("bob");

        Order order = new Order();
        order.setId(orderService.getOrders().get(0).getId());
        order.setName("Jean-Bob");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson=ow.writeValueAsString(order);

        mvc.perform(put("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(orderService.getOrders().get(0).getId())))
                .andExpect(jsonPath("$.name", is("Jean-Bob")));
    }

    @Test
    void deleteOrder() throws Exception {
        orderService.addOrder("bob");
        orderService.addOrder("bob2");

        mvc.perform(delete("/order/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        List<Order> orders = orderService.getOrders();
        System.out.println("size" + orders.size());
        assertEquals(orders.size(), 1);
        System.out.println("val" + orders.get(0));
        assertEquals(orders.get(0).getId(), 1);
    }

}
