package com.example.block15kafkaApp1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    @Autowired
    KafkaProducerApp1 kafkaProducer;

    @PostMapping("/add/{topic}")
    public void addIdCustomer(@PathVariable String topic, @RequestBody String body)
    {
        kafkaProducer.sendMessage(topic,body);
    }
}
