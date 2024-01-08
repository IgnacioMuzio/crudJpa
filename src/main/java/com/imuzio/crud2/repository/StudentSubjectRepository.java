package com.imuzio.crud2.repository;

import com.imuzio.crud2.model.entity.Student;
import com.imuzio.crud2.model.entity.StudentSubject;
import com.imuzio.crud2.model.entity.Subject;
import com.imuzio.crud2.projection.StudentsGradeProjection;
import com.imuzio.crud2.projection.SubjectsGradeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentSubjectRepository extends JpaRepository<StudentSubject,Integer> {

    @Query("Select stu.firstName,stu.lastName,ss.grade from Student stu join StudentSubject ss on stu.id= ss.student.id where ss.subject.id = ?1")
    List<StudentsGradeProjection> getStudentsGradesBySubjectId(Integer id);

    @Query("SELECT sub.name, ss.grade " +
            "FROM Subject sub " +
            "JOIN StudentSubject ss ON sub.id = ss.subject.id " +
            "WHERE ss.student.id = ?1")
    List<SubjectsGradeProjection> getSubjectsGradesByStudentId(Integer id);

    @Query("Select ss from StudentSubject ss where ss.student.id = ?1 and ss.subject.id = ?2")
    List<StudentSubject> findByStudentIdAndSubjectId(Integer studentId, Integer subjectId);
}
