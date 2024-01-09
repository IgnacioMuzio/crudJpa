package com.imuzio.crud2.service;

import com.imuzio.crud2.exception.DuplicatedNameSubjectException;
import com.imuzio.crud2.exception.DuplicatedSubjectInStudentException;
import com.imuzio.crud2.exception.StudentNotFoundException;
import com.imuzio.crud2.exception.SubjectNotFoundException;
import com.imuzio.crud2.model.dto.SubjectDto;
import com.imuzio.crud2.model.entity.StudentSubject;
import com.imuzio.crud2.model.entity.Subject;
import com.imuzio.crud2.projection.StudentsGradeProjection;

import java.util.List;
import java.util.Map;

public interface SubjectService {

    List<SubjectDto> getSubjects();

    SubjectDto getSubjectById(Integer id) throws SubjectNotFoundException;
    Subject save (SubjectDto subjectDto, Integer id) throws DuplicatedNameSubjectException;

    void delete(Integer id);

    SubjectDto subjectDtoBuilder(Subject subject);

    Subject subjectBuilder(SubjectDto subjectDto, Integer id);

    void checkName (SubjectDto subjectDto, Integer id) throws DuplicatedNameSubjectException;

    List<StudentSubject> addStudent(Integer subjectId, Integer studentId, Float grade) throws StudentNotFoundException, SubjectNotFoundException, DuplicatedSubjectInStudentException;

    Map<String,List<StudentsGradeProjection>> getStudentsGrades (String subjectName) throws SubjectNotFoundException;
}
