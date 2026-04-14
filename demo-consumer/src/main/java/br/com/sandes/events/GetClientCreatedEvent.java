package br.com.sandes.events;

import java.util.UUID;

public record GetClientCreatedEvent(
		UUID id,
		String clientName,
		String email) {

}
