package com.peach.rabbitmq.producer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Furniture {
    private String colour;
    private String material;
    private String name;
    private int price;
}
