package com.example.demo.kafka;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.example.demo.events.CreateClientEvent;

@Component
public class ClientEventProducer {
	
	@Value(value = "${kafka-topic}")
	private String topicName;
	
	private KafkaTemplate<String, CreateClientEvent> kafkaTemplate;

	public ClientEventProducer(KafkaTemplate<String, CreateClientEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publishClientCreatedEvent(CreateClientEvent event) {
		CompletableFuture<SendResult<String, CreateClientEvent>> future = 
				kafkaTemplate.send(topicName, event);
		
		future.whenComplete((result, ex) -> {
			if(ex == null) {
				System.out.println(
						"Mensagem enviada=[" + event + "] with offset=[" + result.getRecordMetadata().offset() + "]");
			}
			else {
				System.out.println(
						"Unable to send message=[" + event + "] due to : " + ex.getMessage());
			}
		});
	}
}
