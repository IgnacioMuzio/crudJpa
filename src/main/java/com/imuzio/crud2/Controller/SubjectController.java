package com.imuzio.crud2.Controller;

import com.imuzio.crud2.exceptions.DuplicatedNameSubjectException;
import com.imuzio.crud2.exceptions.DuplicatedSubjectInStudentException;
import com.imuzio.crud2.exceptions.StudentNotFoundException;
import com.imuzio.crud2.exceptions.SubjectNotFoundException;
import com.imuzio.crud2.model.dto.SubjectDto;
import com.imuzio.crud2.model.entity.StudentSubject;
import com.imuzio.crud2.model.entity.Subject;
import com.imuzio.crud2.projection.StudentsGradeProjection;
import com.imuzio.crud2.service.SubjectService;
import com.imuzio.crud2.service.impl.SubjectServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return new ResponseEntity<Subject>(subjectService.save(subjectDto,null),HttpStatus.CREATED);
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<Subject> update (@Valid @RequestBody SubjectDto subjectDto, @PathVariable("subjectId") Integer id) throws DuplicatedNameSubjectException {
        return new ResponseEntity<Subject>(subjectService.save(subjectDto,id),HttpStatus.CREATED);
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
    public ResponseEntity<List<StudentsGradeProjection>> getStudentsGrades (@PathVariable("subjectName") String subjectName) throws SubjectNotFoundException {
        return new ResponseEntity<List<StudentsGradeProjection>>(subjectService.getStudentsGrades(subjectName),HttpStatus.OK);
    }
}
