package com.example.block15kafkaApp2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerApp2 {
    @KafkaListener(topics = "${message.topic.name}", groupId = "${message.group.name}")
    public void listenTopic1(String message) {
        System.out.println("Recieved Message of topic1 in  listener: " + message);
    }

    @KafkaListener(topics = "${message.topic.name2}", groupId = "${message.group.name}")
    public void listenTopic2(String message) {
        System.out.println("Recieved Message of topic2 in  listener "+message);
    }
}
