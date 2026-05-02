package br.com.sandes.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import br.com.sandes.controller.dtos.CreatePostDTO;
import br.com.sandes.controller.dtos.CreatedPostDTO;
import br.com.sandes.services.PostService;

@RestController
@RequestMapping("consumer")
public class PostController {

	private final PostService service;

	public PostController(PostService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<CreatedPostDTO> findAllPosts() {
		return service.findAllPosts();
	}
	
	@PostMapping
	public CreatedPostDTO createPost(
			@RequestBody CreatePostDTO postDto) {
		return service.createPost(postDto);
	}

	@GetMapping(value = "/{userId}")
	public List<CreatedPostDTO> findAllPostsByUserId(@PathVariable UUID userId) {
		return service.findPostsByUserId(userId);
	}
}
