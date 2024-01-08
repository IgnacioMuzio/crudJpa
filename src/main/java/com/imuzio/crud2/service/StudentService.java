package com.imuzio.crud2.service;

import com.imuzio.crud2.exceptions.DuplicatedDniStudentException;
import com.imuzio.crud2.exceptions.StudentNotFoundException;
import com.imuzio.crud2.model.dto.StudentDto;
import com.imuzio.crud2.model.entity.Student;
import com.imuzio.crud2.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List <StudentDto> getStudents(){
        return studentRepository.findAll().stream().map(this::studentDtoBuilder).toList();
    }

    public StudentDto getStudentById(Integer id) throws StudentNotFoundException{
        Optional<Student> student = studentRepository.findById(id);

        if(!student.isPresent()){
            throw new StudentNotFoundException("Student is not available");
        }
        return studentDtoBuilder(student.get());
    }

    public Student save (StudentDto studentDto, Integer id) throws DuplicatedDniStudentException {
        checkDni(studentDto,id);
        Student student = studentBuilder(studentDto,id);
        return studentRepository.save(student);
    }

    public void delete(Integer id){
        studentRepository.deleteById(id);
    }

    private StudentDto studentDtoBuilder(Student student){
        StudentDto studentDto = new StudentDto();

        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setDni(student.getDni());

        return studentDto;
    }

    private Student studentBuilder(StudentDto studentDto, Integer id) throws DuplicatedDniStudentException {
        Student student = new Student();

        student.setId(id);
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setDni(studentDto.getDni());

        return student;
    }

    private void checkDni (StudentDto studentDto, Integer id) throws DuplicatedDniStudentException{
        List <Student> students = studentRepository.findAll();
        for(Student student : students){
            if(studentDto.getDni().equals(student.getDni()) && !Objects.equals(id, student.getId())){
                throw new DuplicatedDniStudentException("Dni number already in use...");
            }
        }
    }
}
