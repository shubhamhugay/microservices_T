package com.rogerstore.ecomorderservice.service;

import com.rogerstore.ecomorderservice.client.InventoryClient;
import com.rogerstore.ecomorderservice.dto.Inventory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final InventoryClient inventoryClient;

    public InventoryService(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    @Retryable(maxAttempts =3,retryFor = RuntimeException.class,backoff = @Backoff(delay = 2000))
   public Inventory getInventory(String productId)
   {
       System.out.println("calling Inventory service for productId :" + productId);
       return inventoryClient.getInventory(productId);

   }


}

