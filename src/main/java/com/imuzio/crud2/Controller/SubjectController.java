package com.imuzio.crud2.Controller;

import com.imuzio.crud2.exceptions.DuplicatedNameSubjectException;
import com.imuzio.crud2.exceptions.SubjectNotFoundException;
import com.imuzio.crud2.model.dto.SubjectDto;
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
    public void update (@Valid @RequestBody SubjectDto subjectDto, @PathVariable("subjectId") Integer id) throws DuplicatedNameSubjectException {
        subjectService.save(subjectDto,id);
    }

    @DeleteMapping("/{subjectId}")
    public void delete(@PathVariable("subjectId") Integer id){
        subjectService.delete(id);
    }
}
