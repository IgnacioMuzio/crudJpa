package com.imuzio.crud2.Controller;

import com.imuzio.crud2.exceptions.SubjectNotFoundException;
import com.imuzio.crud2.model.entity.Subject;
import com.imuzio.crud2.service.SubjectService;
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
    public ResponseEntity<List<Subject>> getAll(){
        return new ResponseEntity<List<Subject>>(subjectService.getSubjects(), HttpStatus.OK);
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<Subject> getById(@PathVariable("subjectId") Integer id) throws SubjectNotFoundException {
        return new ResponseEntity<Subject>(subjectService.getSubjectById(id),HttpStatus.OK);
    }

    @PostMapping(value = "/create",consumes={"application/json"})
    public ResponseEntity<Subject> create(@Valid @RequestBody Subject subject){
        return new ResponseEntity<Subject>(subjectService.save(subject),HttpStatus.CREATED);
    }

    @PutMapping("/update/{subjectId}")
    public void update (@RequestBody Subject subject){
        subjectService.save(subject);
    }

    @DeleteMapping("/{subjectId}")
    public void delete(@PathVariable("subjectId") Integer id){
        subjectService.delete(id);
    }
}
