package com.imuzio.crud2.service;

import com.imuzio.crud2.exception.DuplicatedDniStudentException;
import com.imuzio.crud2.exception.DuplicatedSubjectInStudentException;
import com.imuzio.crud2.exception.StudentNotFoundException;
import com.imuzio.crud2.exception.SubjectNotFoundException;
import com.imuzio.crud2.model.dto.StudentDto;
import com.imuzio.crud2.model.entity.Student;
import com.imuzio.crud2.model.entity.StudentSubject;
import com.imuzio.crud2.projection.SubjectsGradeProjection;

import java.util.List;
import java.util.Map;

public interface StudentService {

    List<StudentDto> getStudents();

    StudentDto getStudentById(Integer id) throws StudentNotFoundException;

    Student create (StudentDto studentDto) throws DuplicatedDniStudentException;
    Student update (StudentDto studentDto, Integer id) throws DuplicatedDniStudentException;

    void delete(Integer id);

    StudentDto studentDtoBuilder(Student student);

    Student studentBuilder(StudentDto studentDto, Integer id);

    void checkDni (StudentDto studentDto, Integer id) throws DuplicatedDniStudentException;

    List<StudentSubject> addSubject(Integer studentId, Integer subjectId, Float grade) throws StudentNotFoundException, SubjectNotFoundException, DuplicatedSubjectInStudentException;

    Map<String,List<SubjectsGradeProjection>> getSubjectsGrade (Integer dni) throws StudentNotFoundException;
}
