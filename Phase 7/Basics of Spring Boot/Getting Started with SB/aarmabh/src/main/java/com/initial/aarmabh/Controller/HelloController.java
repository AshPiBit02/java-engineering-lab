package com.initial.aarmabh.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String sayHello(){
        return "Hi, this is Aashish from PU";
    }

    @GetMapping("/greet")
    public String Greet(){
        return "<h1>Hello! Sir</h1> ";
    }


}
