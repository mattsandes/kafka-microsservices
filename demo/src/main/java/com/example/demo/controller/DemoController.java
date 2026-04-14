package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping
	public List<CreatedClientDTO> findAllUsers() {
		return service.getAllCllients();
	}
	
	@PostMapping
	public CreatedClientDTO postMessages(@RequestBody CreateClientsDTO dto) {
		return service.createClients(dto);
	}
}
