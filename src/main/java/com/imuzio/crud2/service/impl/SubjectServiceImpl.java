package com.imuzio.crud2.service.impl;


import com.imuzio.crud2.exceptions.DuplicatedNameSubjectException;
import com.imuzio.crud2.exceptions.DuplicatedSubjectInStudentException;
import com.imuzio.crud2.exceptions.StudentNotFoundException;
import com.imuzio.crud2.exceptions.SubjectNotFoundException;
import com.imuzio.crud2.model.dto.SubjectDto;
import com.imuzio.crud2.model.entity.Student;
import com.imuzio.crud2.model.entity.StudentSubject;
import com.imuzio.crud2.model.entity.Subject;
import com.imuzio.crud2.projection.StudentsGradeProjection;
import com.imuzio.crud2.repository.StudentRepository;
import com.imuzio.crud2.repository.StudentSubjectRepository;
import com.imuzio.crud2.repository.SubjectRepository;
import com.imuzio.crud2.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentSubjectRepository studentSubjectRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<SubjectDto> getSubjects(){
        return subjectRepository.findAll().stream().map(this::subjectDtoBuilder).toList();
    }

    public SubjectDto getSubjectById(Integer id) throws SubjectNotFoundException {
        Optional<Subject> subject = subjectRepository.findById(id);

        if(!subject.isPresent()){
            throw new SubjectNotFoundException("Subject is not available");
        }
        return subjectDtoBuilder(subject.get());
    }

    public Subject save (SubjectDto subjectDto, Integer id) throws DuplicatedNameSubjectException {
        checkName(subjectDto,id);
        Subject subject = subjectBuilder(subjectDto,id);
        return subjectRepository.save(subject);
    }

    public void delete(Integer id){
        subjectRepository.deleteById(id);
    }

    public SubjectDto subjectDtoBuilder(Subject subject){
        SubjectDto subjectDto = new SubjectDto();

        subjectDto.setName(subject.getName());

        return subjectDto;
    }

    public Subject subjectBuilder(SubjectDto subjectDto, Integer id) {
        Subject subject = new Subject();

        subject.setId(id);
        subject.setName(subjectDto.getName());

        return subject;
    }

    public void checkName(SubjectDto subjectDto, Integer id) throws DuplicatedNameSubjectException {
        List <Subject> subjects = subjectRepository.findAll();
        for(Subject subject : subjects){
            if(subjectDto.getName().equals(subject.getName()) && !Objects.equals(id, subject.getId())){
                throw new DuplicatedNameSubjectException("Subject name already in use...");
            }
        }
    }

    public List<StudentSubject> addStudent(Integer subjectId, Integer studentId, Float grade) throws StudentNotFoundException, SubjectNotFoundException, DuplicatedSubjectInStudentException {
        Subject subject = subjectBuilder(getSubjectById(subjectId),subjectId);
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new StudentNotFoundException("Student is not found"));
        if(!studentSubjectRepository.findByStudentIdAndSubjectId(studentId,subjectId).isEmpty()){
            throw new DuplicatedSubjectInStudentException("Student is already related ");
        }
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setStudent(student);
        studentSubject.setSubject(subject);
        studentSubject.setGrade(grade);

        subject.getStudents().add(studentSubject);

        subjectRepository.save(subject);

        return subject.getStudents();
    }

    public List<StudentsGradeProjection> getStudentsGrades (String subjectName) throws SubjectNotFoundException {
        Subject subject = subjectRepository.findByName(subjectName).orElseThrow(()->new SubjectNotFoundException("Subject "+ subjectName + "not found"));
        List<StudentsGradeProjection> studentsGrades = new ArrayList<>();
        studentsGrades.addAll(studentSubjectRepository.getStudentsGradesBySubjectId(subject.getId()));
        return  studentsGrades;
    }
}
