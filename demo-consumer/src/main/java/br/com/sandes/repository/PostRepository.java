package br.com.sandes.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.sandes.data.Post;

public interface PostRepository extends JpaRepository<Post, UUID>{

    List<Post> findAllByUserid(UUID userId);
}
