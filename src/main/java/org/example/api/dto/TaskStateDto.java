package org.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.store.entities.TaskStateEntity;

import java.time.Instant;
import java.util.List;

public record TaskStateDto(
        Long id,
        String name,
        Long orderliness,
        @JsonProperty("created_at") Instant createdAt,
        List<TaskDto> tasks
) {
}
