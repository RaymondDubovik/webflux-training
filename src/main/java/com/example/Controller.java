package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class Controller {

    private final Service service;
    
    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping
    public DeferredResult<String> getNames() {
        DeferredResult<String> deferredResult = new DeferredResult<>();

        // It is important to execute the networking code on a separate thread, so that the HTTP thread is freed
        // Fixed thread pool used for simplicity, it is not a good solution for ever-changing incoming http request count
//        ExecutorService executorService = Executors.newFixedThreadPool(25);
//        executorService.submit(() -> service.longApiRequest(deferredResult));
        new Thread(() -> service.longApiRequest(deferredResult)).start();
        
        return deferredResult;
    }
}
