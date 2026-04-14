package br.com.sandes.repository;

import br.com.sandes.data.PostClients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostClientRepository extends JpaRepository<PostClients, UUID> {

    boolean existsByClientId(UUID userId);
}
