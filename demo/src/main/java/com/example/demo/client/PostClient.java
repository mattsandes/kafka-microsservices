package com.example.demo.client;

import com.example.demo.controller.dtos.CreatedPostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "consumer", url = "http://localhost:8000/consumer")
public interface PostClient {

    @GetMapping(value = "/{userId}")
    public List<CreatedPostDTO> findAllPostsByUserId(@PathVariable UUID userId);
}
