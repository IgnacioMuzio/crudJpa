package com.imuzio.crud2.model.dto;

import jakarta.persistence.Column;
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
    @Column(name="first_name")
    private String firstName;

    @NotEmpty(message = "LastName is needed")
    @Column(name="last_name")
    private String lastName;

    @NotNull(message = "Dni is needed")
    private Integer dni;

}
