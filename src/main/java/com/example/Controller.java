package com.example;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final Service service;
    
    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping
    public String getNames() throws IOException {
        return service.longApiRequest().getMessage();
    }
}
