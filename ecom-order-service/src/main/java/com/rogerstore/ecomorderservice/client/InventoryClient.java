package com.rogerstore.ecomorderservice.client;

import com.rogerstore.ecomorderservice.config.InventoryFeignClientConfig;
import com.rogerstore.ecomorderservice.dto.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ecom-inventory-service",
configuration = InventoryFeignClientConfig.class
)
public interface InventoryClient {


    @GetMapping("/inventory/{productId}")
    Inventory getInventory(@PathVariable String productId);

    @PostMapping("/inventory")
    String updateInventory(@RequestBody Inventory inventory);
}
