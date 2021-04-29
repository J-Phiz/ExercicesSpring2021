package org.jps.jpsave;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jps.jpsave.dto.OrderRequest;
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

    @BeforeEach
    void deleteDb() {
        System.out.println("Je suis dans deleteDb");
        List<Integer> ids = orderService.getOrders().stream().map(Order::getId).collect(Collectors.toList());
        ids.forEach(id -> orderService.deleteOrderById(id));
    }

    @Test
    void getOrder() throws Exception {
        orderService.addOrderWithCity(new OrderRequest("bob", 47.36, 0.74));

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
        orderService.addOrderWithCity(new OrderRequest("bob", 47.36, 0.74));
        orderService.addOrderWithCity(new OrderRequest("bob2", 48.36, 0.74));

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
        System.out.println("Je suis dans createOrder");
        OrderRequest orderRequest = new OrderRequest("bob", 47.36, 0.74);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson=ow.writeValueAsString(orderRequest);

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
        orderService.addOrderWithCity(new OrderRequest("bob", 47.36, 0.74));

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setId(orderService.getOrders().get(0).getId());
        orderRequest.setName("Jean-Bob");
        orderRequest.setLat(48.36);
        orderRequest.setLon(0.74);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson=ow.writeValueAsString(orderRequest);

        mvc.perform(put("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
                .andExpect(status().isOk());
    }

    @Test
    void deleteOrder() throws Exception {
        orderService.addOrderWithCity(new OrderRequest("bob", 47.36, 0.74));
        orderService.addOrderWithCity(new OrderRequest("bob2", 48.36, 0.74));

        List<Order> ordersBefore = orderService.getOrders();
        Integer beforeId = ordersBefore.get(1).getId();

        mvc.perform(delete("/order/"+ordersBefore.get(0).getId()))
                .andExpect(status().isOk());

        List<Order> ordersAfter = orderService.getOrders();
        assertEquals(1, ordersAfter.size());
        assertEquals(beforeId, ordersAfter.get(0).getId());
    }

}
