package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.clients.OrderClient;
import com.ecommerce.inventoryservice.dto.InventoryRequest;
import com.ecommerce.inventoryservice.dto.InventoryResponse;
import com.ecommerce.inventoryservice.dto.OrderResponse;
import com.ecommerce.inventoryservice.entities.Inventory;
import com.ecommerce.inventoryservice.exception.InventoryNotFoundException;
import com.ecommerce.inventoryservice.kafka.OrderPlacedEvent;
import com.ecommerce.inventoryservice.kafka.PaymentEvent;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final OrderClient orderClient;

    public InventoryResponse checkInventory(String skuCode){
          Inventory inventory=inventoryRepository.findBySkuCode(skuCode).orElseThrow(()->new InventoryNotFoundException(skuCode));
          InventoryResponse inventoryResponse=new InventoryResponse();
          inventoryResponse.setSkuCode(inventory.getSkuCode());
          inventoryResponse.setAvailableStock(inventory.getQuantity());
          inventoryResponse.setAvailable(inventory.getQuantity() > 0);
          return inventoryResponse;
    }

    public InventoryResponse addInventory(InventoryRequest inventoryRequest){
        Inventory inventory=modelMapper.map(inventoryRequest,Inventory.class);
        Inventory saved=inventoryRepository.save(inventory);

        InventoryResponse inventoryResponse=InventoryResponse.builder()
                .availableStock(saved.getQuantity())
                .available(saved.getQuantity()>0)
                .build();
        return inventoryResponse;
    }

    public InventoryResponse updateInventory(String skuCode, InventoryRequest inventoryRequest) {
        Inventory inventory=inventoryRepository.findBySkuCode(skuCode).orElseThrow(()->new RuntimeException("no inventory found"));
        inventory.setQuantity(inventoryRequest.getQuantity());
        Inventory saved=inventoryRepository.save(inventory);
        return InventoryResponse.builder()
                .availableStock(saved.getQuantity())
                .skuCode(saved.getSkuCode())
                .available(saved.getQuantity()>0)
                .build();
    }

    public void updateInventoryStock(PaymentEvent event){
        OrderResponse orderResponse=orderClient.getSkuCodeByOrderId(event.getOrderId());
        if(orderResponse==null)
            throw new RuntimeException("no order found");



        Inventory inventory=inventoryRepository.findBySkuCode(orderResponse.getSkuCode()).orElseThrow(()->new RuntimeException("no inventory found"));
        int orderedQuantity=orderResponse.getQuantity();
        int availableQuantity=inventory.getQuantity();
        if(orderedQuantity>availableQuantity)
            throw new RuntimeException("product not in stock");
        inventory.setQuantity(availableQuantity-orderedQuantity);
        inventoryRepository.save(inventory);
    }
}
