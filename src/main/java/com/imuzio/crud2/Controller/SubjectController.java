package com.imuzio.crud2.Controller;

import com.imuzio.crud2.exception.DuplicatedNameSubjectException;
import com.imuzio.crud2.exception.DuplicatedSubjectInStudentException;
import com.imuzio.crud2.exception.StudentNotFoundException;
import com.imuzio.crud2.exception.SubjectNotFoundException;
import com.imuzio.crud2.model.dto.SubjectDto;
import com.imuzio.crud2.model.entity.StudentSubject;
import com.imuzio.crud2.model.entity.Subject;
import com.imuzio.crud2.projection.StudentsGradeProjection;
import com.imuzio.crud2.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAll(){
        return new ResponseEntity<List<SubjectDto>>(subjectService.getSubjects(), HttpStatus.OK);
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectDto> getById(@PathVariable("subjectId") Integer id) throws SubjectNotFoundException {
        return new ResponseEntity<SubjectDto>(subjectService.getSubjectById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Subject> create(@Valid @RequestBody SubjectDto subjectDto) throws DuplicatedNameSubjectException {
        return new ResponseEntity<Subject>(subjectService.create(subjectDto),HttpStatus.CREATED);
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<Subject> update (@Valid @RequestBody SubjectDto subjectDto, @PathVariable("subjectId") Integer id) throws DuplicatedNameSubjectException {
        return new ResponseEntity<Subject>(subjectService.update(subjectDto,id),HttpStatus.OK);
    }

    @DeleteMapping("/{subjectId}")
    public void delete(@PathVariable("subjectId") Integer id){
        subjectService.delete(id);
    }

    @PutMapping("/addStudent/{subjectId}/{studentId}/{grade}")
    public ResponseEntity<List<StudentSubject>> addSubject( @PathVariable("subjectId") Integer subjectId,@PathVariable("studentId") Integer studentId, @PathVariable("grade") Float grade) throws SubjectNotFoundException, DuplicatedSubjectInStudentException, StudentNotFoundException {
        return new ResponseEntity<List<StudentSubject>>(subjectService.addStudent(subjectId,studentId,grade),HttpStatus.CREATED);
    }

    @GetMapping("/studentgrade/{subjectName}")
    public ResponseEntity<Map<String,List<StudentsGradeProjection>>> getStudentsGrades (@PathVariable("subjectName") String subjectName) throws SubjectNotFoundException {
        return new ResponseEntity<Map<String,List<StudentsGradeProjection>>>(subjectService.getStudentsGrades(subjectName),HttpStatus.OK);
    }
}
