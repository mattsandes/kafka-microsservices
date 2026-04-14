package br.com.sandes.config;

import br.com.sandes.events.GetClientCreatedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	private static final Logger log = LoggerFactory.getLogger(KafkaConsumerConfig.class);

	@Value(value = "${spring.kafka.backoff.interval}")
	private Long interval;

	@Value(value = "${spring.kafka.backoff.max_failure}")
	private Long maxAttempts;
	
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
	DefaultErrorHandler errorHandler () {
		ExponentialBackOff fixedBackOff = new ExponentialBackOff(interval, maxAttempts);

        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler (
				(consumerRecord, exception) -> {
					log.error("Erro durante o consumo da mensagem do topico {}: {}",
							consumerRecord.topic(), exception.getMessage()
					);
				}, fixedBackOff
		);

		defaultErrorHandler.addNotRetryableExceptions(
				IllegalArgumentException.class
		);

		return defaultErrorHandler;
	}
	
	@Bean
	ConcurrentKafkaListenerContainerFactory<String, GetClientCreatedEvent>
		kafkaListenerContainerFactory () {

		ConcurrentKafkaListenerContainerFactory<String, GetClientCreatedEvent> factory =
				new ConcurrentKafkaListenerContainerFactory<>();

		factory.setConsumerFactory(consumerFactory());

		factory.setConcurrency(3);
		factory.setCommonErrorHandler(errorHandler());
		factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);

		return factory;
	}
}
