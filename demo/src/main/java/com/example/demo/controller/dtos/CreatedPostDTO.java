package com.example.demo.controller.dtos;

import java.util.UUID;

public record CreatedPostDTO(
        UUID id,
        String postTitle,
        String postContent,
        UUID userId) {

}
