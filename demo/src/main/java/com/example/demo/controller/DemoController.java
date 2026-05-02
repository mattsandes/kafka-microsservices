package com.example.demo.controller;

import java.util.List;
import java.util.UUID;

import com.example.demo.controller.dtos.ClientPostsDTO;
import org.springframework.web.bind.annotation.*;

import com.example.demo.controller.dtos.CreateClientsDTO;
import com.example.demo.controller.dtos.CreatedClientDTO;
import com.example.demo.servicd.DemoService;

@RestController
@RequestMapping("/demo")
public class DemoController {

	private DemoService service;

	public DemoController(DemoService service) {
		this.service = service;
	}
	
	@GetMapping(value = "/{userId}")
	public List<ClientPostsDTO> findAllUsers(@PathVariable UUID userId) {
		return service.getAllClients(userId);
	}
	
	@PostMapping
	public CreatedClientDTO postMessages(@RequestBody CreateClientsDTO dto) {
		return service.createClients(dto);
	}
}
