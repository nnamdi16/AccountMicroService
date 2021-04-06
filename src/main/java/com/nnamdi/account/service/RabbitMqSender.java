//package com.nnamdi.account.service;
//
//import com.nnamdi.account.model.Account;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class RabbitMqSender {
//
//    private RabbitTemplate rabbitTemplate;
//
//    @Autowired
//    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    @Value("${spring.rabbitmq.template.exchange}")
//    private String exchange;
//
//    @Value("${spring.rabbitmq.template.routing-key}")
//    private String routingKey;
//
//    public void send(String account) {
//        rabbitTemplate.convertAndSend(exchange,routingKey,account);
//        System.out.println("Sending the message =" + account);
//    }
//
//}
