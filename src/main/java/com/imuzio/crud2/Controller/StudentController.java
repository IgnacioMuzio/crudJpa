package com.imuzio.crud2.Controller;

import com.imuzio.crud2.exceptions.StudentNotFoundException;
import com.imuzio.crud2.model.entity.Student;
import com.imuzio.crud2.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (path = "/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAll(){
        return new ResponseEntity<List<Student>>(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getById(@PathVariable("studentId") Integer id) throws StudentNotFoundException {
        return new ResponseEntity<Student>(studentService.getStudentById(id),HttpStatus.OK);
    }

    @PostMapping(value = "/create",consumes={"application/json"})
    public ResponseEntity<Student> create(@Valid @RequestBody Student student){
        return new ResponseEntity<Student>(studentService.save(student),HttpStatus.CREATED);
    }

    @PutMapping("/update/{studentId}")
    public void update (@RequestBody Student student){
        studentService.save(student);
    }

    @DeleteMapping("/{studentId}")
    public void delete(@PathVariable("studentId") Integer id){
        studentService.delete(id);
    }
}
