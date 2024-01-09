package com.imuzio.crud2.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record StudentDto(
        @NotEmpty(message = "FirstName is needed") String firstName,
        @NotEmpty(message = "LastName is needed") String lastName,
        @NotNull(message = "Dni is needed") Integer dni
) {
}
