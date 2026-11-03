package com.rogerstore.ecomorderservice.controllers;

import com.rogerstore.ecomorderservice.service.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final RestTemplate restTemplate;

    public OrderController(OrderService orderService, RestTemplate restTemplate) {
        this.orderService = orderService;
        this.restTemplate = restTemplate;
    }


    @PostMapping("/{productId}")
    public String orderPlace(@PathVariable String productId) throws ExecutionException, InterruptedException {

        return orderService.placeOrder(productId);
    }
}
