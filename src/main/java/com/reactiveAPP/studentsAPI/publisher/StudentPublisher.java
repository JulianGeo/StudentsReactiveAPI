package com.reactiveAPP.studentsAPI.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactiveAPP.studentsAPI.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class StudentPublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;


    public StudentPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }



    public void publish(String studentID, String courseID, String eventType) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(new StudentEvent(studentID, courseID, eventType));
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, message);
    }


    public void publishError(Throwable errorEvent){
    }

}
