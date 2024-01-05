package com.imuzio.crud2.repository;

import com.imuzio.crud2.model.entity.StudentSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubject,Integer> {
}
