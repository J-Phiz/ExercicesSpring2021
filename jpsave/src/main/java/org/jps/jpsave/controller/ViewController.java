package org.jps.jpsave.controller;

import org.jps.jpsave.dto.OrderRequest;
import org.jps.jpsave.exception.NotFoundException;
import org.jps.jpsave.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public String viewGet(Model model) {
        model.addAttribute("orderRequest", new OrderRequest());
        model.addAttribute("orders", orderService.getOrders());
        return "view";
    }

    @GetMapping("{id}")
    public String viewDescription(Model model, @PathVariable("id") int id) throws NotFoundException {
        model.addAttribute("order", orderService.getOrderById(id));
        return "view-by-id";
    }

    @PostMapping(params="create")
    public String viewPostAdd(Model model, @ModelAttribute("orderRequest") OrderRequest orderRequest) {
        orderService.addOrderWithCity(orderRequest);
        return "redirect:/view";
    }

    @PostMapping("/delete/{id}")
    public String viewPostSuppress(Model model, @PathVariable("id") int id) throws NotFoundException {
        orderService.deleteOrderById(id);
        return "redirect:/view";
    }
}
