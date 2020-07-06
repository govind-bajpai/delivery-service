package com.assignment.delivery.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Item implements Serializable {
    
    @JsonProperty("itemId")
    private Long itemId;
    @JsonProperty("name")
    private String name;
}
