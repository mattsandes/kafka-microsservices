package br.com.sandes.controller.dtos;

import java.util.UUID;

public record CreatePostDTO(
		String postTitle,
		String postContent,
		UUID userId
		) {

}
