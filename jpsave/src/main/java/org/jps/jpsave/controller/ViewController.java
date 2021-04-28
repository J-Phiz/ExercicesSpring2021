package org.jps.jpsave.controller;

import org.jps.jpsave.dto.OrderRequest;
import org.jps.jpsave.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public String viewGet(Model model) {
        System.out.println("Je suis dans le get");
        model.addAttribute("orderRequest", new OrderRequest());
        model.addAttribute("orders", orderService.getOrders());
        return "view";
    }

    @PostMapping()
    public String viewPost(Model model, @ModelAttribute("orderRequest") OrderRequest orderRequest) {
        System.out.println("Je suis dans le post");
        orderService.addOrderWithCity(orderRequest);
        return "view";
    }

}
