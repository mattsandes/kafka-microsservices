package com.example.demo.servicd;

import java.util.List;
import java.util.UUID;

import com.example.demo.client.PostClient;
import com.example.demo.controller.dtos.ClientPostsDTO;
import org.springframework.stereotype.Service;

import com.example.demo.controller.dtos.CreateClientsDTO;
import com.example.demo.controller.dtos.CreatedClientDTO;
import com.example.demo.data.Clients;
import com.example.demo.events.CreateClientEvent;
import com.example.demo.kafka.ClientEventProducer;
import com.example.demo.repository.DemoRepository;

@Service
public class DemoService {

	private final PostClient postClient;
	private final ClientEventProducer producer;
	private final DemoRepository repository;

	public DemoService(
			PostClient postClient,
			ClientEventProducer producer,
			DemoRepository repository) {
		this.postClient = postClient;
		this.repository = repository;
		this.producer = producer;
	}
	
	public List<ClientPostsDTO> getAllClients(UUID userId) {
		var posts = postClient.findAllPostsByUserId(userId);

		return repository.findAll()
				.stream().map(user -> new ClientPostsDTO(
						user.getId(),
						user.getClientName(),
						user.getEmail(),
						posts
				)).toList();
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
