package com.reactiveAPP.studentsAPI.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactiveAPP.studentsAPI.config.RabbitConfig;
import com.reactiveAPP.studentsAPI.domain.dto.StudentDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class StudentPublisher {
    private final RabbitTemplate rabbitTemplate;
    //private final DirectExchange exchange;
    private final ObjectMapper objectMapper;


    public StudentPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
    }



    public void publish(String studentID, String courseID) throws JsonProcessingException {
        //String message = objectMapper.writeValueAsString("Book with id "+bookDTO.getId()+" has been lended to student with id "+id);
        String message = objectMapper.writeValueAsString(new StudentEvent(studentID, courseID, "enroll"));
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, message);
    }


    public void publishError(Throwable errorEvent){
    }

}
