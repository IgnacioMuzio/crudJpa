package com.imuzio.crud2.service.impl;

import com.imuzio.crud2.exception.DuplicatedDniStudentException;
import com.imuzio.crud2.exception.DuplicatedSubjectInStudentException;
import com.imuzio.crud2.exception.StudentNotFoundException;
import com.imuzio.crud2.exception.SubjectNotFoundException;
import com.imuzio.crud2.model.dto.StudentDto;
import com.imuzio.crud2.model.entity.Student;
import com.imuzio.crud2.model.entity.StudentSubject;
import com.imuzio.crud2.model.entity.Subject;
import com.imuzio.crud2.projection.SubjectsGradeProjection;
import com.imuzio.crud2.repository.StudentRepository;
import com.imuzio.crud2.repository.StudentSubjectRepository;
import com.imuzio.crud2.repository.SubjectRepository;
import com.imuzio.crud2.service.StudentService;
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
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StudentSubjectRepository studentSubjectRepository;

    Logger logger = Logger.getLogger(StudentServiceImpl.class);

    public List <StudentDto> getStudents(){
        List <StudentDto> students = studentRepository.findAll().stream().map(this::studentDtoBuilder).toList();
        students.stream().forEach(student -> logger.info(student.getFirstName() + " "  + student.getLastName()));
        return students;
    }

    public StudentDto getStudentById(Integer id) throws StudentNotFoundException{
        Optional<Student> student = studentRepository.findById(id);
        if(!student.isPresent()){
            throw new StudentNotFoundException("Student is not available");
        }
        logger.info(student.get().getFirstName() + " "  + student.get().getLastName());
        return studentDtoBuilder(student.get());
    }

    public Student create (StudentDto studentDto) throws DuplicatedDniStudentException {
        checkDni(studentDto,null);
        Student student = studentBuilder(studentDto,null);
        logger.info("Student created");
        return studentRepository.save(student);
    }

    public Student update (StudentDto studentDto, Integer id) throws DuplicatedDniStudentException {
        checkDni(studentDto,id);
        Student student = studentBuilder(studentDto,id);
        logger.info("Student updated");
        return studentRepository.save(student);
    }

    public void delete(Integer id){
        studentRepository.deleteById(id);
    }

    public StudentDto studentDtoBuilder(Student student){
        StudentDto studentDto = new StudentDto();

        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setDni(student.getDni());

        return studentDto;
    }

    public Student studentBuilder(StudentDto studentDto, Integer id){
        Student student = new Student();

        student.setId(id);
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setDni(studentDto.getDni());

        return student;
    }

    public void checkDni (StudentDto studentDto, Integer id) throws DuplicatedDniStudentException{
        Optional <Student> student = studentRepository.findByDni(studentDto.getDni());
        logger.debug("Validating dni");
        if(student.isPresent()){
            if(!Objects.equals(id, student.get().getId())){
                logger.error("Dni already in use");
                throw new DuplicatedDniStudentException("Dni number already in use...");
            }
        }
        logger.debug("Dni validated");
    }

    public List<StudentSubject> addSubject(Integer studentId, Integer subjectId, Float grade) throws StudentNotFoundException, SubjectNotFoundException, DuplicatedSubjectInStudentException {
        Student student = studentBuilder(getStudentById(studentId),studentId);
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(()->new SubjectNotFoundException("Subject not found"));
        if(!studentSubjectRepository.findByStudentIdAndSubjectId(studentId,subjectId).isEmpty()){
            throw new DuplicatedSubjectInStudentException("Subject is already related ");
        }
        StudentSubject studentSubject = new StudentSubject();
        studentSubject.setStudent(student);
        studentSubject.setSubject(subject);
        studentSubject.setGrade(grade);

        student.getSubjects().add(studentSubject);

        studentRepository.save(student);
        logger.info("Subject added to Student");
        return student.getSubjects();
    }

    public Map<String,List<SubjectsGradeProjection>> getSubjectsGrade (Integer dni) throws StudentNotFoundException {
        Student student = studentRepository.findByDni(dni).orElseThrow(()->new StudentNotFoundException("Student with dni number: " + dni + "was not found"));
        List<SubjectsGradeProjection> subjectGrades = new ArrayList<>();
        subjectGrades.addAll(studentSubjectRepository.getSubjectsGradesByStudentId(student.getId()));
        Map<String,List<SubjectsGradeProjection>> map = new HashMap<>();
        map.put(student.getFirstName()+ " " + student.getLastName(),subjectGrades);
        return map;
    }
}
