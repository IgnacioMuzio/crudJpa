package com.imuzio.crud2.repository;

import com.imuzio.crud2.model.entity.Student;
import com.imuzio.crud2.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    Optional<Student> findByDni(Integer dni);
}
