package com.imuzio.crud2.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDto {

    @NotEmpty(message = "Name of the subject is needed")
    private String name;
}
