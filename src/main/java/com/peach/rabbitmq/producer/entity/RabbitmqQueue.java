package com.peach.rabbitmq.producer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RabbitmqQueue {
    private long messages;
    private String name;

    public boolean isDirty(){
        return messages > 0;
    }
}
