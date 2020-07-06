package com.assignment.delivery.repository;

import com.assignment.delivery.enums.Status;
import com.assignment.delivery.model.DeliveryPerson;

import java.util.List;


public interface DeliveryPersonRepository {
    public DeliveryPerson saveOrUpdate(DeliveryPerson person);
    public DeliveryPerson getDeliveryPersonById(Long id);
    public List<DeliveryPerson> getDeliveryPersonByStatus(Status status);
    public List<DeliveryPerson> getAllDeliveryPersons();
    public List<DeliveryPerson> getDeliveryPersonByOrderId(Long orderId);
}
