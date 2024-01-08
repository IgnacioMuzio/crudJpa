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

    @Query(value = "Select stu.first_name,stu.last_name,sis.grade from students stu join student_subject sis on sis.student_id = stu.id where sis.subject_id = :id", nativeQuery = true)
    List<StudentsGradeProjection> getStudentsGradesBySubjectId(Integer id);

    @Query(value = "Select sub.name,sis.grade from subjects sub join student_subject sis on sis.subject_id = sub.id where sis.student_id = :id", nativeQuery = true)
    List<SubjectsGradeProjection> getSubjectsGradesByStudentId(Integer id);

    @Query("Select ss from StudentSubject ss where ss.student.id = ?1 and ss.subject.id = ?2")
    List<StudentSubject> findByStudentIdAndSubjectId(Integer studentId, Integer subjectId);
}
