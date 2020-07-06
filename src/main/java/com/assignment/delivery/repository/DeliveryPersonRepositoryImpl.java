package com.assignment.delivery.repository;

import com.assignment.delivery.enums.Status;
import com.assignment.delivery.model.DeliveryPerson;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class DeliveryPersonRepositoryImpl implements DeliveryPersonRepository{
    
    private final Map<Long,DeliveryPerson> deliveryPersonData = new HashMap<>();
    private final AtomicLong id = new AtomicLong(0);
    
    @Override
    public DeliveryPerson saveOrUpdate(DeliveryPerson person) {
        if(null == person.getId()){
            person.setId(id.incrementAndGet());
        }
        deliveryPersonData.put(person.getId(),person);
        return person;
    }
    
    @Override
    public DeliveryPerson getDeliveryPersonById(Long id) {
        return deliveryPersonData.get(id);
    }
    
    @Override
    public List<DeliveryPerson> getDeliveryPersonByStatus(Status status) {
        return deliveryPersonData.values().stream().filter(e -> status.equals(e.getStatus())).collect(Collectors.toList());
    }
    
    @Override
    public List<DeliveryPerson> getAllDeliveryPersons() {
        return new ArrayList<>(deliveryPersonData.values());
    }
    
    @Override
    public List<DeliveryPerson> getDeliveryPersonByOrderId(Long orderId) {
        return deliveryPersonData.values().stream().filter(e -> orderId.equals(e.getOrderId())).collect(Collectors.toList());
    }
}
