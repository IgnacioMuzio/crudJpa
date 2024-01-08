package com.imuzio.crud2.service;

import com.imuzio.crud2.exceptions.StudentNotFoundException;
import com.imuzio.crud2.exceptions.SubjectNotFoundException;
import com.imuzio.crud2.model.entity.Student;
import com.imuzio.crud2.model.entity.Subject;
import com.imuzio.crud2.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getSubjects(){
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Integer id) throws SubjectNotFoundException {
        Optional<Subject> subject = subjectRepository.findById(id);

        if(!subject.isPresent()){
            throw new SubjectNotFoundException("Subject is not available");
        }
        return subject.get();
    }

    public Subject save (Subject subject){
        return subjectRepository.save(subject);
    }

    public void delete(Integer id){
        subjectRepository.deleteById(id);
    }
}
