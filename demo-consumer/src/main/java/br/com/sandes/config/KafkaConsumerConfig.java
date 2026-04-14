package br.com.sandes.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import br.com.sandes.events.GetClientCreatedEvent;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	
	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;
	
	@Value(value = "${spring.kafka.consumer.group-id}")
	private String groupId;

	@Bean
	ConsumerFactory<String, GetClientCreatedEvent> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				JacksonJsonDeserializer.class);
		
		props.put(JacksonJsonDeserializer.TRUSTED_PACKAGES, "*");
		
		props.put(JacksonJsonDeserializer.USE_TYPE_INFO_HEADERS, false);
		
		return new DefaultKafkaConsumerFactory<>(
				props,
				new StringDeserializer(),
				new JacksonJsonDeserializer<>(GetClientCreatedEvent.class));
	}
	
	@Bean
	ConcurrentKafkaListenerContainerFactory<String, GetClientCreatedEvent>
		kafkaListenerContainerFactory () {
		
		ConcurrentKafkaListenerContainerFactory<String, GetClientCreatedEvent> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		
		factory.setConsumerFactory(consumerFactory());
		
		return factory;
 	}
}
