package br.com.sandes.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.sandes.events.GetClientCreatedEvent;
import br.com.sandes.services.projections.ClientProjectionService;
import br.com.sandes.utils.KafkaTopics;

@Component
public class DemoKafkaConsumer {
	
	private final ClientProjectionService projectionService;
	
	public DemoKafkaConsumer(ClientProjectionService projectionService) {
		this.projectionService = projectionService;
	}

	@KafkaListener(
			topics = KafkaTopics.TOPIC_TEST,
			groupId = "${spring.kafka.consumer.group-id}")
	public void listenDemoGroup(
			@Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
			@Payload(required = false) GetClientCreatedEvent event) {
		System.out.println(
				"Mensagem recebida do service: " + event + "\n" + 
				"Id da partição: " + partition
		);
		
		if(event != null) {
			projectionService.saveClient(event);
            System.out.println("Projeção após salvar: " + projectionService.findAll());
		}
	}
}
