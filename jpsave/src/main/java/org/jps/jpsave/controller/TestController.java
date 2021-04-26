package org.jps.jpsave.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping
    public String addTest() {
        System.out.println("Je suis dans le post");
        return "Post is valid";
    }

    @GetMapping("/{name}")
    public String getTest(
            @PathVariable("name") String name,
            @RequestParam("param") String param,
            @RequestBody() String body
    ) {
        System.out.println("Je suis dans le get");

        return "name=" + name + "param=" + param + "body=" + body;
    }

}
