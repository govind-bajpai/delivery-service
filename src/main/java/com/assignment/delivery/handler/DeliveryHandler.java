package com.assignment.delivery.handler;

import com.assignment.delivery.enums.OrderStatus;
import com.assignment.delivery.enums.Status;
import com.assignment.delivery.exception.DeliveryPersonNotFoundException;
import com.assignment.delivery.model.DeliveryPerson;
import com.assignment.delivery.model.Order;
import com.assignment.delivery.model.OrderPersonMapRequest;
import com.assignment.delivery.repository.DeliveryPersonRepository;
import org.apache.commons.lang3.StringUtils;
import org.graalvm.compiler.replacements.PEGraphDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class DeliveryHandler {
    
    @Autowired
    private DeliveryPersonRepository repository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${order.url:http://localhost:8084/orders/}")
    private String orderUri;
    
    @Value("${order.status.update.endpoint:%s/status/}")
    private String statusEndPoint;
    
    
    public List<DeliveryPerson> getDeliveryPersons(Status status) {
        return repository.getDeliveryPersonByStatus(status);
    }
    
    public DeliveryPerson updateDeliveryPersonStatus(Long id, Status status) {
        DeliveryPerson person = repository.getDeliveryPersonById(id);
        if(null == person){
            throw new DeliveryPersonNotFoundException("Delivery Person not found for id: "+id);
        }
        person.setStatus(status);
        return repository.saveOrUpdate(person);
    }
    
    public DeliveryPerson addDeliveryPerson(DeliveryPerson person) {
        person.setStatus(Status.IDLE);
        person.setTatRemaining(null);
        person.setOrderId(null);
        person.setIsActive(false);
        return repository.saveOrUpdate(person);
    }
    
    public DeliveryPerson getDeliveryPerson(Long id) {
        DeliveryPerson person = repository.getDeliveryPersonById(id);
        if(null == person){
            throw new DeliveryPersonNotFoundException("Delivery Person not found for id: "+id);
        }
        return person;
    }
    
    public List<DeliveryPerson> getAllDeliveryPersons() {
        return repository.getAllDeliveryPersons();
    }
    
    public String mapOrderToDeliveryPerson(OrderPersonMapRequest request){
        validateOrderPersonMapRequest(request);
        DeliveryPerson person = repository.getDeliveryPersonById(request.getDeliveryPersonId());
        if(person.getOrderId() != null){
            //if(person.getTatRemaining() )
        }
        Order order = null;
        try {
            order = fetchOrder(request.getOrderId());
        }catch (Exception e){
            System.out.println("Error while fetching the order for orderId"+request.getOrderId());
        }
        person.setOrderId(order.getId());
        person.setOrderAssignedTime(new Date());
        person.setIsActive(true);
        person.setStatus(Status.OUT_FOR_DELIVERY);
        person.setTatRemaining(order.getTatDelivery());
        updateOrderStatus(order.getId(), OrderStatus.ACCEPTED.name());
        if(null == order){
            throw new DeliveryPersonNotFoundException("Delivery Person not found for id: "+request.getDeliveryPersonId());
        }
        return "Mapped Successfully";
    }
    
    private Order updateOrderStatus(Long orderId, String status) {
        Order order = null;
        try {
            String url = StringUtils.join(orderUri,String.format(statusEndPoint,orderId));
            HttpEntity<String> entity = new HttpEntity<>(status);
            order = restTemplate.postForObject(url,entity,Order.class);
        } catch (RestClientException ex) {
            System.out.println("Error while fetching the order "+ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        } catch (Exception e) {
            System.out.println("Error while requesting the order "+e.getMessage());
        }
        return order;
    }
    
    private Order fetchOrder(String orderId) throws Exception {
        Order order = null;
        try {
        String url = StringUtils.join(orderUri,orderId);
        order = restTemplate.getForObject(url,Order.class);
        } catch (RestClientException ex) {
            System.out.println("Error while fetching the order "+ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        } catch (Exception e) {
            System.out.println("Error while requesting the order "+e.getMessage());
            throw new Exception("Error while requesting the order Service");
        }
        return order;
    }
    
    private void validateOrderPersonMapRequest(OrderPersonMapRequest request) {
        if(null == request.getDeliveryPersonId() || null == request.getOrderId()){
            throw new IllegalArgumentException("DeliveryPersonId or OrderId missing");
        }
    }
    
    public static void main(String[] args) {
        RestTemplate template = new RestTemplate();
        Order order = template.getForObject("http://localhost:8084/orders/1",Order.class);
    }
    
    
    public List<DeliveryPerson> updateDeliveryStatus() {
        List<DeliveryPerson> persons = repository.getDeliveryPersonByStatus(Status.OUT_FOR_DELIVERY);
        List<DeliveryPerson> result = new ArrayList<>();

        for(DeliveryPerson person : persons){
            Long remainingTime = calculateTatRemaining(person);
            if(remainingTime < 0){
                updateOrderStatus(person.getOrderId(), OrderStatus.DELIVERED.name());
                updatePerson(person);
            }else{
                person.setTatRemaining((remainingTime/(60*1000)));
            }
            result.add(repository.getDeliveryPersonById(person.getId()));
        }
        return  result;
    }
    public Long calculateTatRemaining(DeliveryPerson person){
        Date currentDate = new Date();
        return (person.getTatRemaining()*60*1000 + person.getOrderAssignedTime().getTime() - currentDate.getTime());
    }
    
    private void updatePerson(DeliveryPerson person) {
        person.setIsActive(false);
        person.setOrderId(null);
        person.setStatus(Status.IDLE);
        person.setTatRemaining(null);
        person.setOrderAssignedTime(null);
        repository.saveOrUpdate(person);
    }
}
