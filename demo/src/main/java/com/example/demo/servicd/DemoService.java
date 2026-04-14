package com.example.demo.servicd;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.controller.dtos.CreateClientsDTO;
import com.example.demo.controller.dtos.CreatedClientDTO;
import com.example.demo.data.Clients;
import com.example.demo.events.CreateClientEvent;
import com.example.demo.kafka.ClientEventProducer;
import com.example.demo.repository.DemoRepository;

@Service
public class DemoService {
	
	private ClientEventProducer producer;
	private DemoRepository repository;

	public DemoService(
			ClientEventProducer producer,
			DemoRepository repository) {
		this.repository = repository;
		this.producer = producer;
	}
	
	public List<CreatedClientDTO> getAllCllients() {
		return repository.findAll()
				.stream()
				.map(user -> new CreatedClientDTO(
						user.getId(),
						user.getClientName(),
						user.getEmail()))
				.toList();
	}
	
	public CreatedClientDTO createClients(CreateClientsDTO dto) {
		if(dto.clientName().isBlank() || dto.email().isBlank()) {
			throw new RuntimeException("Atributes can be null or blank");
		}
		
		Clients client = new Clients(dto.clientName(), dto.email());
		
		var createdClient = repository.save(client);
		
		CreateClientEvent event = new CreateClientEvent(
				createdClient.getId(),
				createdClient.getClientName(),
				createdClient.getEmail()
		);
		
		producer.publishClientCreatedEvent(event);
		
		return new CreatedClientDTO(
				createdClient.getId(),
				createdClient.getClientName(),
				createdClient.getEmail());
	}
}
