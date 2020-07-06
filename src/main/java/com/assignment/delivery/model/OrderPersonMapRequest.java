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
public class OrderPersonMapRequest implements Serializable {
    
    private static final long serialVersionUID = 6156896887L;
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("deliveryPersonId")
    private Long deliveryPersonId;
    
}
