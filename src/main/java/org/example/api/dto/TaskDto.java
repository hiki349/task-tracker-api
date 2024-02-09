package org.example.api.dto;

public record TaskDto(
        Long id,
        String name,
        String description
) {
}
