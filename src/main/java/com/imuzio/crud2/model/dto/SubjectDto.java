package com.imuzio.crud2.model.dto;

import jakarta.validation.constraints.NotEmpty;

public record SubjectDto(
        @NotEmpty(message = "Name of the subject is needed") String name
) {
}
