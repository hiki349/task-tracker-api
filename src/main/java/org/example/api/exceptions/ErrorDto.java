package org.example.api.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorDto(
        String error,
        @JsonProperty("error_description") String errorDescription
) {
}
