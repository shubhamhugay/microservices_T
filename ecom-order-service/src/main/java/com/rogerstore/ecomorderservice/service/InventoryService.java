package com.rogerstore.ecomorderservice.service;

import com.rogerstore.ecomorderservice.client.InventoryClient;
import com.rogerstore.ecomorderservice.dto.Inventory;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final InventoryClient inventoryClient;

    public InventoryService(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

//    @Retryable(maxAttempts =3,retryFor = RuntimeException.class,backoff = @Backoff(delay = 2000))
//    @RateLimiter(name="inventoryService",fallbackMethod ="fallbackMethod" )
    @CircuitBreaker(name="inventoryServiceCircuitBreaker" ,fallbackMethod = "circuitBreakerfallbackMethod")
   public Inventory getInventory(String productId)
   {
       System.out.println("calling Inventory service for productId :" + productId);
       return inventoryClient.getInventory(productId);

   }

   public Inventory circuitBreakerfallbackMethod(Long productId, Throwable throwable)
   {
       System.out.println("fallback method called for productId"+ productId);
       return new Inventory(productId.toString(),0);
   }
}

