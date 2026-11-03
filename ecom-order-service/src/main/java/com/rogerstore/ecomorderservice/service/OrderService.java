package com.rogerstore.ecomorderservice.service;

import com.rogerstore.ecomorderservice.client.InventoryClient;
import com.rogerstore.ecomorderservice.dto.Inventory;
import com.rogerstore.ecomorderservice.exception.MyCustomRuntimeException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

@Service
public class OrderService {
    private final RestClient restClient;
    private final RestTemplate restTemplate;
    private final InventoryClient inventoryClient;
    private final InventoryService inventoryService;


    public OrderService(RestClient restClient, RestTemplate restTemplate, InventoryClient inventoryClient, InventoryService inventoryService) {
        this.restClient = restClient;
        this.restTemplate = restTemplate;
        this.inventoryClient = inventoryClient;
        this.inventoryService = inventoryService;
    }


    public String placeOrder(String productId) throws ExecutionException, InterruptedException {
//        String response = restTemplate.getForObject(
//                "http://localhost:8081/inventory/" + productId,
//                String.class
//        );


//       ResponseEntity<Inventory> entity = restClient
//                   .get()
//                    .uri("http://localhost:8081/inventory/{productId}",productId)
//                    .retrieve()
//                   .toEntity(Inventory.class);


//        ResponseEntity<Inventory> entity =restClient
//                   .get()
//                    .uri("http://localhost:8081/inventory/{productId}",productId)
//                    .retrieve()
//                .onStatus(HttpStatusCode::is4xxClientError,((request, response) ->
//                {
//                    throw new MyCustomRuntimeException(response.getStatusCode(),response.getHeaders());
//                }))
//                   .toEntity(Inventory.class);
        // Exchange Example For Demo
       /* Object exchange = restClient.get()
                .uri("http://localhost:8081/inventory/{productId}", productId)
                .exchange(((clientRequest, clientResponse) ->
                {
                    if (clientResponse.getStatusCode().is4xxClientError()) {
                        throw new MyCustomRuntimeException(clientResponse.getStatusCode(), clientResponse.getHeaders());
                    } else {
                        return clientResponse.getBody();
                    }
                }));*/


        Inventory inventory = inventoryService.getInventory(productId).get();
        updateInventory(inventory);
//        return entity.getBody()!=null && entity.getBody().getQuantity()>0?
//                     "Order PLaced" : "order not PLaced";
        return inventory.getQuantity() > 0 ?
                "Order PLaced" : "order not PLaced";
    }

    private void updateInventory(Inventory inventory) {
        if(inventory.getQuantity()<=0)
        {
          return;
        }
        inventory.setQuantity(inventory.getQuantity() - 1);
        inventoryClient.updateInventory(inventory);
//                 restClient.post()
//                .uri("http://localhost:8081/inventory")
//                .body(inventory)
//                .retrieve()
//                .toBodilessEntity();
    }


}
