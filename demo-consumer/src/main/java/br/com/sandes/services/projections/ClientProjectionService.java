package br.com.sandes.services.projections;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.sandes.events.GetClientCreatedEvent;

@Service
public class ClientProjectionService {

	private final List<GetClientCreatedEvent> clients = new ArrayList<>();
	
	public void saveClient(GetClientCreatedEvent event) {
		boolean exists = clients.stream()
				.anyMatch(client -> client.id().equals(event.id()));
		
		if(!exists) {
			clients.add(event);
		}
	}
	
	public boolean existsById(UUID id) {
		return clients.stream()
				.anyMatch(client -> client.id().equals(id));
	}
	
	public GetClientCreatedEvent findById(UUID id) {
		return clients.stream()
				.filter(client -> client.id().equals(id))
				.findFirst()
				.orElse(null);
	}
	
	public List<GetClientCreatedEvent> findAll() {
		return clients;
	}
}
