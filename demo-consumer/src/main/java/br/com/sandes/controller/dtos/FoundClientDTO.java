package br.com.sandes.controller.dtos;

import java.util.UUID;

public record FoundClientDTO(
        UUID id,
        String userName
) {
}
