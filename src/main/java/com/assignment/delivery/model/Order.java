package com.assignment.delivery.model;

import com.assignment.delivery.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order implements Serializable {
    
    private static final long serialVersionUID = 6156896887L;
    
    @JsonProperty("id")
    private Long id;
    @JsonProperty("status")
    private OrderStatus status;
    @JsonProperty("createdAt")
    private Date createdAt;
    @JsonProperty("deliveryPersonId")
    private Long deliveryPersonId;
    @JsonProperty("tatDelivery")
    private Long tatDelivery;
    
}