package com.rogerstore.ecomorderservice.service;

import com.rogerstore.ecomorderservice.client.InventoryClient;
import com.rogerstore.ecomorderservice.dto.Inventory;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
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

//    @TimeLimiter(name= "inventoryServiceTimeLimiter" , fallbackMethod = "timeLimiterFallbackMethod")
//    @Bulkhead(name = "inventoryServiceBulkhead",fallbackMethod ="bulkHeadFallbackMethod" )
//    public CompletableFuture< Inventory> getInventory(String productId)
//   {
//       System.out.println("calling Inventory service for productId :" + productId);
//     return CompletableFuture.supplyAsync(()->inventoryClient.getInventory(productId));
//
//   }
//    public  Inventory getInventory(String productId)
//    {
//        System.out.println("calling Inventory service for productId :"
//                + productId
//                +"|Thread "
//                + Thread.currentThread().getName()
//
//        );
//        return inventoryClient.getInventory(productId);
//
//    }
//
//   public  Inventory bulkHeadFallbackMethod(Long productId, Throwable throwable)
//   {
//       System.out.println("Bulkhead Rejected called for productId"+ productId + " |Reason "
//       +throwable.getClass().getSimpleName());
//       return  new Inventory(productId.toString(),0);
//   }
@Bulkhead(
        name = "inventoryThreadPool",
        type = Bulkhead.Type.THREADPOOL,
        fallbackMethod = "bulkHeadFallbackMethod"
)
public CompletableFuture<Inventory> getInventory(String productId) {

    System.out.println(
            "Calling Inventory service for productId: "
                    + productId
                    + " | Thread: "
                    + Thread.currentThread().getName()
    );

    return CompletableFuture.completedFuture(
            inventoryClient.getInventory(productId)
    );
}

    public CompletableFuture<Inventory> bulkHeadFallbackMethod(
            String productId,
            Throwable throwable
    ) {

        System.out.println(
                "Bulkhead fallback called for productId: "
                        + productId
                        + " | Reason: "
                        + throwable.getClass().getSimpleName()
        );

        return CompletableFuture.completedFuture(
                new Inventory(productId, 0)
        );
    }

}

