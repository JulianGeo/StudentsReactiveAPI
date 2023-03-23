package com.reactiveAPP.studentsAPI.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {


    public static final String STUDENTS_QUEUE = "students.queue";
    public static final String EXCHANGE = "students-exchange-events";
    public static final String ROUTING_KEY = "events.students.routing.key";

    public static final String GENERAL_QUEUE = "general.queue";
    public static final String ROUTING_KEY_GENERAL = "events.#";


    @Bean
    public RabbitAdmin rabbitAdmin(RabbitTemplate rabbitTemplate) {
        var admin =  new RabbitAdmin(rabbitTemplate);
        admin.declareExchange(new TopicExchange(EXCHANGE));
        return admin;
    }

    //Set bean to queue
    @Bean
    public Queue eventsQueue(){
        return new Queue(STUDENTS_QUEUE);
    }

    @Bean
    public Queue eventsGeneralQueue(){
        return new Queue(GENERAL_QUEUE);
    }

    //Set bean to exchange
    @Bean
    public TopicExchange eventsExchange(){
        return new TopicExchange(EXCHANGE);
    }

    //Set beans related to binding
    @Bean
    public Binding eventsBinding(){
        return BindingBuilder.bind(this.eventsQueue()).to(this.eventsExchange()).with(ROUTING_KEY);
    }

    @Bean
    public Binding eventsGeneralBinding(){
        return BindingBuilder.bind(this.eventsGeneralQueue()).to(this.eventsExchange()).with(ROUTING_KEY_GENERAL);
    }

    //TODO: do i need these methods?
/*    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }*/
}
