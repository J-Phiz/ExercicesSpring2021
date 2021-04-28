package org.jps.jpsave.controller;

import org.jps.jpsave.dto.OrderRequest;
import org.jps.jpsave.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public String viewGet(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        return "view";
    }

//    @PostMapping()
//    public String view(Model model) {
//        return "view";
//    }

}
