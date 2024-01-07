package com.imuzio.crud2.service;

import com.imuzio.crud2.exceptions.StudentNotFoundException;
import com.imuzio.crud2.model.entity.Student;
import com.imuzio.crud2.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List <Student> getStudents(){
        return studentRepository.findAll();
    }

    public Student getStudentById(Integer id) throws StudentNotFoundException{
        Optional<Student> student = studentRepository.findById(id);

        if(!student.isPresent()){
            throw new StudentNotFoundException("Student is not available");
        }
        return student.get();
    }

    public Student save (Student student){
        return studentRepository.save(student);
    }

    public void delete(Integer id){
        studentRepository.deleteById(id);
    }
}
