package com.peach.rabbitmq.producer.controller;

import com.peach.rabbitmq.producer.entity.Employee;
import com.peach.rabbitmq.producer.entity.Furniture;
import com.peach.rabbitmq.producer.entity.Picture;
import com.peach.rabbitmq.producer.producer.EmployeeJsonProducer;
import com.peach.rabbitmq.producer.producer.FurnitureProducer;
import com.peach.rabbitmq.producer.producer.FurnitureTopicProducer;
import com.peach.rabbitmq.producer.producer.PictureProducer;
import com.peach.rabbitmq.producer.producer.RabbitProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin
public class MessageController {

    private final RabbitProducer rabbitProducer;
    private final PictureProducer pictureProducer;
    private final EmployeeJsonProducer employeeJsonProducer;
    private final FurnitureProducer furnitureProducer;
    private final FurnitureTopicProducer furnitureTopicProducer;

    public MessageController(
            RabbitProducer rabbitProducer,
            PictureProducer pictureProducer,
            EmployeeJsonProducer employeeJsonProducer,
            FurnitureProducer furnitureProducer,
            FurnitureTopicProducer furnitureTopicProducer
    ) {
        this.rabbitProducer = rabbitProducer;
        this.pictureProducer = pictureProducer;
        this.employeeJsonProducer = employeeJsonProducer;
        this.furnitureProducer = furnitureProducer;
        this.furnitureTopicProducer = furnitureTopicProducer;
    }

    @PostMapping("/hello")
    public ResponseEntity<Map<String, String>> sendHello(@RequestParam String name) {
        rabbitProducer.sendHello(name);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Message sent");
        response.put("message", "Hello " + name);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/picture")
    public ResponseEntity<Map<String, String>> sendPicture(@RequestBody Picture picture) {
        pictureProducer.sendJsonMessage(picture);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Picture message sent");
        response.put("pictureName", picture.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/employee")
    public ResponseEntity<Map<String, String>> sendEmployee(@RequestBody Employee employee) {
        employeeJsonProducer.sendJsonMessage(employee);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Employee message sent");
        response.put("employeeName", employee.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/furniture")
    public ResponseEntity<Map<String, String>> sendFurniture(@RequestBody Furniture furniture) {
        furnitureProducer.sendMessage(furniture);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Furniture message sent");
        response.put("furnitureName", furniture.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/furniture/topic")
    public ResponseEntity<Map<String, String>> sendFurnitureTopic(@RequestBody Furniture furniture) {
        furnitureTopicProducer.sendJsonMessage(furniture);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Furniture topic message sent");
        response.put("furnitureName", furniture.getName());
        response.put("routingKey", furniture.getColour().toLowerCase() + "." + 
                                  furniture.getMaterial().toLowerCase() + "." + 
                                  (furniture.getPrice() >= 100 ? "expensive" : "budget"));
        return ResponseEntity.ok(response);
    }
}