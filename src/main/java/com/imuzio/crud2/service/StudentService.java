package com.imuzio.crud2.service;

import com.imuzio.crud2.exceptions.DuplicatedDniStudentException;
import com.imuzio.crud2.exceptions.DuplicatedSubjectInStudentException;
import com.imuzio.crud2.exceptions.StudentNotFoundException;
import com.imuzio.crud2.exceptions.SubjectNotFoundException;
import com.imuzio.crud2.model.dto.StudentDto;
import com.imuzio.crud2.model.entity.Student;
import com.imuzio.crud2.model.entity.StudentSubject;
import com.imuzio.crud2.projection.SubjectsGradeProjection;

import java.util.List;

public interface StudentService {

    List<StudentDto> getStudents();

    StudentDto getStudentById(Integer id) throws StudentNotFoundException;

    Student save (StudentDto studentDto, Integer id) throws DuplicatedDniStudentException;

    void delete(Integer id);

    StudentDto studentDtoBuilder(Student student);

    Student studentBuilder(StudentDto studentDto, Integer id);

    void checkDni (StudentDto studentDto, Integer id) throws DuplicatedDniStudentException;

    List<StudentSubject> addSubject(Integer studentId, Integer subjectId, Float grade) throws StudentNotFoundException, SubjectNotFoundException, DuplicatedSubjectInStudentException;

    List<SubjectsGradeProjection> getSubjectsGrade (Integer id) throws StudentNotFoundException;
}
