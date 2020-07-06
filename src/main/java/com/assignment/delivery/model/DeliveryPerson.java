package com.assignment.delivery.model;

import com.assignment.delivery.enums.Status;
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
public class DeliveryPerson implements Serializable {
    
    private static final long serialVersionUID = 6156896887L;
    
    private Long id;
    @JsonProperty("orderId")
    private Long orderId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("isActive")
    private Boolean isActive;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("tatRemaining")
    private Long tatRemaining;
    @JsonProperty("orderAssignedTime")
    private Date orderAssignedTime;
}