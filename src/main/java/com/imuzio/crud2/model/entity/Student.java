package com.imuzio.crud2.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "FirstName is needed")
    @Column(name="first_name")
    private String firstName;

    @NotEmpty(message = "LastName is needed")
    @Column(name="last_name")
    private String lastName;

    @NotEmpty(message = "Dni is needed")
    @Size(min = 8,max = 8)
    private String dni;

    @OneToMany (mappedBy ="student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StudentSubject> subjects;
}
