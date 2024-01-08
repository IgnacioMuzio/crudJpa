package com.imuzio.crud2.repository;

import com.imuzio.crud2.model.entity.Student;
import com.imuzio.crud2.model.entity.StudentSubject;
import com.imuzio.crud2.model.entity.Subject;
import com.imuzio.crud2.model.entity.projection.StudentsGradeProjection;
import com.imuzio.crud2.model.entity.projection.SubjectsGradeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubject,Integer> {

    @Query("Select stu.firstName,stu.lastName,ss.grade from Student stu join StudentSubject ss on stu.id= ss.student.id where ss.subject.id = ?1")
    List<StudentsGradeProjection> getStudentsGradesBySubjectId(Integer id);

    @Query("Select sub.name,ss.grade from Subject sub join StudentSubject ss on sub.id = ss.student.id where ss.student.id = ?1")
    List<SubjectsGradeProjection> getSubjectsGradesByStudentId(Integer id);

    StudentSubject findByStudentAndSubject(Student student, Subject subject);
}
