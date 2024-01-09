package com.imuzio.crud2.Controller;

import com.imuzio.crud2.exception.DuplicatedDniStudentException;
import com.imuzio.crud2.exception.DuplicatedSubjectInStudentException;
import com.imuzio.crud2.exception.StudentNotFoundException;
import com.imuzio.crud2.exception.SubjectNotFoundException;
import com.imuzio.crud2.model.dto.StudentDto;
import com.imuzio.crud2.model.entity.Student;
import com.imuzio.crud2.model.entity.StudentSubject;
import com.imuzio.crud2.projection.SubjectsGradeProjection;
import com.imuzio.crud2.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping (path = "/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll(){
        return new ResponseEntity<List<StudentDto>>(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> getById(@PathVariable("studentId") Integer id) throws StudentNotFoundException {
        return new ResponseEntity<StudentDto>(studentService.getStudentById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student> create(@Valid @RequestBody StudentDto studentDto) throws DuplicatedDniStudentException {
        return new ResponseEntity<Student>(studentService.create(studentDto,null),HttpStatus.CREATED);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> update (@Valid @RequestBody StudentDto studentDto,@PathVariable("studentId") Integer id) throws DuplicatedDniStudentException {
        return new ResponseEntity<Student>(studentService.update(studentDto,id),HttpStatus.OK);
    }

    @DeleteMapping("/{studentId}")
    public void delete(@PathVariable("studentId") Integer id){
        studentService.delete(id);
    }

    @PutMapping("/addSubject/{studentId}/{subjectId}/{grade}")
    public ResponseEntity<List<StudentSubject>> addSubject(@PathVariable("studentId") Integer studentId, @PathVariable("subjectId") Integer subjectId, @PathVariable("grade") Float grade) throws SubjectNotFoundException, DuplicatedSubjectInStudentException, StudentNotFoundException {
        return new ResponseEntity<List<StudentSubject>>(studentService.addSubject(studentId,subjectId,grade),HttpStatus.CREATED);
    }

    @GetMapping("/subjectgrade/{studentDni}")
    public ResponseEntity<Map<String,List<SubjectsGradeProjection>>> getSubjectGrades (@PathVariable("studentDni") Integer studentDni) throws StudentNotFoundException {
        return new ResponseEntity<Map<String,List<SubjectsGradeProjection>>>(studentService.getSubjectsGrade(studentDni),HttpStatus.OK);
    }
}
