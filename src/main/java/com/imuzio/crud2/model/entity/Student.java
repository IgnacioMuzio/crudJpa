package com.imuzio.crud2.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    private Integer dni;

    @OneToMany (mappedBy ="student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StudentSubject> subjects;

    public Student() {
        subjects = new ArrayList<>();
    }
}
