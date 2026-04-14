package br.com.sandes.services.projections;

import br.com.sandes.controller.dtos.FoundClientDTO;
import br.com.sandes.data.PostClients;
import br.com.sandes.events.GetClientCreatedEvent;
import br.com.sandes.repository.PostClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClientProjectionService {

	private final PostClientRepository repository;

	public ClientProjectionService(PostClientRepository repository) {
		this.repository = repository;
	}

	public void saveClient(GetClientCreatedEvent event) {
		boolean exists = repository.existsById(event.id());
		
		if(!exists) {
			PostClients postClientsEntity = new PostClients(
					event.id(),
					event.clientName());

			repository.save(postClientsEntity);
		}
	}
	
	public boolean existsByClientId(UUID id) {
		return repository.existsByClientId(id);
	}
	
	public FoundClientDTO findById(UUID id) {
		return repository.findById(id)
				.map(client -> new FoundClientDTO(
						client.getId(),
						client.getUserName()
				))
				.orElse(null);
	}
	
	public List<FoundClientDTO> findAll() {
		return repository.findAll()
				.stream()
				.map(user -> new FoundClientDTO(
						user.getId(),
						user.getUserName()
				))
				.toList();
	}
}
