package br.com.sandes.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.sandes.controller.dtos.CreatePostDTO;
import br.com.sandes.controller.dtos.CreatedPostDTO;
import br.com.sandes.data.Post;
import br.com.sandes.repository.PostRepository;
import br.com.sandes.services.projections.ClientProjectionService;

@Service
public class PostService {

	private ClientProjectionService projectionService;
	private PostRepository repository;
	
	public PostService(PostRepository repository, ClientProjectionService projectionService) {
		this.repository = repository;
		this.projectionService = projectionService;
	}
	
	public List<CreatedPostDTO> findAllPosts() {
		return repository.findAll()
				.stream()
				.map(post -> new CreatedPostDTO(
						post.getId(),
						post.getPostTitle(),
						post.getContent(),
						post.getUserid()))
				.toList();
	}
	
	public CreatedPostDTO createPost(CreatePostDTO postDto) {
		if(postDto.postTitle().isBlank() || postDto.postContent().isBlank()) {
			throw new RuntimeException("Atributos não podem ser vazios ou nulos");
		}
		
		boolean clientExists = projectionService.existsById(
				postDto.userId());
		
		if(!clientExists) {
			throw new RuntimeException("Usuário não encontrado");
		}
		
		Post post = new Post(
				postDto.postTitle(),
				postDto.postContent(),
				postDto.userId()
		);
		
		Post createdPost = repository.save(post);
		
		return new CreatedPostDTO(
				createdPost.getId(),
				createdPost.getPostTitle(),
				createdPost.getContent(),
				createdPost.getUserid()
		);
	}
}
