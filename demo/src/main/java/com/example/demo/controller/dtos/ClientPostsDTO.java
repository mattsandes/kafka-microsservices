package com.example.demo.controller.dtos;

import java.util.List;
import java.util.UUID;

public record ClientPostsDTO (
        UUID id,
        String clientName,
        String email,
        List<CreatedPostDTO> userPosts) {

}