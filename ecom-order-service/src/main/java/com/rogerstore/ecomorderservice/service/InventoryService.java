package com.rogerstore.ecomorderservice.service;

import com.rogerstore.ecomorderservice.client.InventoryClient;
import com.rogerstore.ecomorderservice.dto.Inventory;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class InventoryService {
    private final InventoryClient inventoryClient;

    public InventoryService(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

//    @Retryable(maxAttempts =3,retryFor = RuntimeException.class,backoff = @Backoff(delay = 2000))
//    @RateLimiter(name="inventoryService",fallbackMethod ="fallbackMethod" )
//    @CircuitBreaker(name="inventoryServiceCircuitBreaker" ,fallbackMethod = "circuitBreakerfallbackMethod")

    @TimeLimiter(name= "inventoryServiceTimeLimiter" , fallbackMethod = "timeLimiterFallbackMethod")
    public CompletableFuture< Inventory> getInventory(String productId)
   {
       System.out.println("calling Inventory service for productId :" + productId);
     return CompletableFuture.supplyAsync(()->inventoryClient.getInventory(productId));

   }

   public CompletableFuture< Inventory> timeLimiterFallbackMethod(Long productId, Throwable throwable)
   {
       System.out.println("fallback method called for productId"+ productId);
       return CompletableFuture.completedFuture(new Inventory(productId.toString(),0));
   }
}

