package com.example.demo.config.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {
	
	@Value(value = "${kafka-topic}")
	private String topicName;

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootStrapAddress;
	
	@Bean
	KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		
		configs.put(
				AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,
				bootStrapAddress
		);
		
		return new KafkaAdmin(configs);
	}
	
	@Bean
	NewTopic topic1() {
		return TopicBuilder.name(topicName)
				.partitions(1)
				.replicas(1)
				.build();
	}
}
