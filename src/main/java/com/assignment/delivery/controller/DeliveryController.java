package com.assignment.delivery.controller;

import com.assignment.delivery.enums.Status;
import com.assignment.delivery.handler.DeliveryHandler;
import com.assignment.delivery.model.DeliveryPerson;
import com.assignment.delivery.model.OrderPersonMapRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/delivery")
public class DeliveryController {
    
    @Autowired
    DeliveryHandler handler;
    
    
    @GetMapping("/{id}")
    public DeliveryPerson getDeliveryPerson(@PathVariable Long id){
        return handler.getDeliveryPerson(id);
        
    }
    
    @PostMapping("/add")
    public DeliveryPerson addDeliveryPerson(@RequestBody DeliveryPerson person){
        return handler.addDeliveryPerson(person);
        
    }
    
    @GetMapping("status/{status}")
    public List<DeliveryPerson> getDeliveryPerson(@PathVariable Status status){
        return handler.getDeliveryPersons(status);
        
    }
    
    @GetMapping("/getAllDeliveryPersons")
    public List<DeliveryPerson> getAllDeliveryPersons(){
        return handler.getAllDeliveryPersons();
        
    }
    
    @PutMapping("/{id}/status")
    public DeliveryPerson updateStatus(@PathVariable(required = true) Long id,@RequestBody(required = true) String status){
        return handler.updateDeliveryPersonStatus(id,Status.valueOf(status));
    }
    
    @PostMapping("/mapOrderToDeliveryPerson")
    public String mapOrderToDeliveryPerson(@RequestBody(required = true) OrderPersonMapRequest request){
        return handler.mapOrderToDeliveryPerson(request);
    }
    
    @PostMapping("/updateDeliveryStatus")
    public List<DeliveryPerson> updateDeliveryStatus(){
        return handler.updateDeliveryStatus();
    }
}
