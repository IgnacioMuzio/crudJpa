package com.imuzio.crud2.service.impl;


import com.imuzio.crud2.exception.DuplicatedNameSubjectException;
import com.imuzio.crud2.exception.DuplicatedSubjectInStudentException;
import com.imuzio.crud2.exception.StudentNotFoundException;
import com.imuzio.crud2.exception.SubjectNotFoundException;
import com.imuzio.crud2.model.dto.SubjectDto;
import com.imuzio.crud2.model.entity.Student;
import com.imuzio.crud2.model.entity.StudentSubject;
import com.imuzio.crud2.model.entity.Subject;
import com.imuzio.crud2.projection.StudentsGradeProjection;
import com.imuzio.crud2.repository.StudentRepository;
import com.imuzio.crud2.repository.StudentSubjectRepository;
import com.imuzio.crud2.repository.SubjectRepository;
import com.imuzio.crud2.service.SubjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    Logger logger = Logger.getLogger(StudentServiceImpl.class);


    public List<SubjectDto> getSubjects(){
        List<SubjectDto> subjectDtos = subjectRepository.findAll().stream().map(this::subjectDtoBuilder).toList();
        subjectDtos.stream().forEach(subjectDto -> logger.info(subjectDto.getName()));
        return subjectDtos;
    }

    public SubjectDto getSubjectById(Integer id) throws SubjectNotFoundException {
        Optional<Subject> subject = subjectRepository.findById(id);

        if(!subject.isPresent()){
            throw new SubjectNotFoundException("Subject is not available");
        }
        logger.info(subject.get().getName());
        return subjectDtoBuilder(subject.get());
    }

    public Subject create (SubjectDto subjectDto) throws DuplicatedNameSubjectException {
        checkName(subjectDto,null);
        Subject subject = subjectBuilder(subjectDto,null);
        logger.info("Subject created");
        return subjectRepository.save(subject);
    }

    public Subject update (SubjectDto subjectDto, Integer id) throws DuplicatedNameSubjectException {
        checkName(subjectDto,id);
        Subject subject = subjectBuilder(subjectDto,id);
        logger.info("Subject updated");
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
        Optional <Subject> subject = subjectRepository.findByName(subjectDto.getName());
        logger.debug("Validating name");
        if(subject.isPresent()){
            if(!Objects.equals(id, subject.get().getId())){
                logger.error("Name already in use");
                throw new DuplicatedNameSubjectException("Subject name already in use...");
            }
        }
        logger.debug("Name validated");
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
        logger.info("Student added to Subject");
        return subject.getStudents();
    }

    public Map<String,List<StudentsGradeProjection>> getStudentsGrades (String subjectName) throws SubjectNotFoundException {
        Subject subject = subjectRepository.findByName(subjectName).orElseThrow(()->new SubjectNotFoundException("Subject "+ subjectName + "not found"));
        List<StudentsGradeProjection> studentsGrades = new ArrayList<>();
        studentsGrades.addAll(studentSubjectRepository.getStudentsGradesBySubjectId(subject.getId()));
        Map<String,List<StudentsGradeProjection>> map = new HashMap<>();
        map.put(subjectName,studentsGrades);
        return  map;
    }
}
