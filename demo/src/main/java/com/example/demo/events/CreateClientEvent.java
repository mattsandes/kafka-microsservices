package com.example.demo.events;

import java.util.UUID;

public record CreateClientEvent(
		UUID id,
		String clientName,
		String email) {

}
