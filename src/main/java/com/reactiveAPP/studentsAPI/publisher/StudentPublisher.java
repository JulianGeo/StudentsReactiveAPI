package com.reactiveAPP.studentsAPI.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactiveAPP.studentsAPI.domain.dto.StudentDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class StudentPublisher {
    private final RabbitTemplate rabbitTemplate;
    //private final DirectExchange exchange;
    private final ObjectMapper objectMapper;


    public StudentPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.rabbitTemplate = rabbitTemplate;
        //this.exchange = exchange;
    }

    public void publish(StudentDTO studentDTO, String id) throws JsonProcessingException {
        //String message = objectMapper.writeValueAsString("Book with id "+bookDTO.getId()+" has been lended to student with id "+id);
        String message = objectMapper.writeValueAsString(new StudentEvent(id, studentDTO, "enroll"));
        rabbitTemplate.convertAndSend("students-exchange-events", "events.students.routing.key", message);

    }


    public void publishError(Throwable errorEvent){
    }
}
