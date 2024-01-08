package com.imuzio.crud2.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {

    @NotEmpty(message = "FirstName is needed")
    private String firstName;

    @NotEmpty(message = "LastName is needed")
    private String lastName;

    @NotNull(message = "Dni is needed")
    private Integer dni;

}
