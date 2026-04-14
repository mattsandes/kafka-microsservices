package br.com.sandes.controller.dtos;

import java.util.UUID;

public record CreatedPostDTO(
		UUID id,
		String postTitle,
		String postContent,
		UUID userId) {

}
