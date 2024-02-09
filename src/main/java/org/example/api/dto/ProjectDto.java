package org.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record ProjectDto(
        Long id,
        String name,
        @JsonProperty("created_at") Instant createdAt
) {
}
