package com.example.demo.controller.dtos;

import java.util.UUID;

public record CreatedClientDTO (
		UUID id,
		String clientName,
		String email) {

}
