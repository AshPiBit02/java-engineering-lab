package com.initial.aarmabh.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;

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

    @GetMapping("/outDemo")
    public void customResponse(HttpServletResponse res) throws IOException{
        PrintWriter out = res.getWriter();
        res.setContentType("text/plain");
        out.println("Hello, this the demo from servlet program");
    }


}
