//package com.nnamdi.account.config;
//
//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableRabbit
//public class RabbitMQConfig {
//
//    @Value("${spring.rabbitmq.host}")
//	private String host;
//
//    @Value("${spring.rabbitmq.username}")
//	private String username;
//
//	@Value("${spring.rabbitmq.password}")
//	private String password;
//
////	@Value("${rabbitmq.url}")
////	private String rabbitMQUri;
//
//	@Bean
//    CachingConnectionFactory connectionFactory() {
//	    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
////		cachingConnectionFactory.setUri(rabbitMQUri);
////		cachingConnectionFactory.setRequestedHeartBeat(30);
////		cachingConnectionFactory.setConnectionTimeout(30);
//		cachingConnectionFactory.setUsername(username);
//		cachingConnectionFactory.setPassword(password);
//
//		return cachingConnectionFactory;
//	}
//
//	@Bean
//	public MessageConverter jsonMessageConverter() {
//    	    return  new Jackson2JsonMessageConverter();
//	}
//
//	@Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//		rabbitTemplate.setMessageConverter(jsonMessageConverter());
//		return rabbitTemplate;
//	}
//
//
//}
