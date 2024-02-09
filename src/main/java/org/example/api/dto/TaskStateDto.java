package org.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record TaskStateDto(
        Long id,
        String name,
        Integer ordinal,
        @JsonProperty("created_at") Instant createdAt
) {
}
