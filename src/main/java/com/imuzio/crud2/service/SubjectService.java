package com.imuzio.crud2.service;


import com.imuzio.crud2.exceptions.DuplicatedNameSubjectException;
import com.imuzio.crud2.exceptions.SubjectNotFoundException;
import com.imuzio.crud2.model.dto.SubjectDto;
import com.imuzio.crud2.model.entity.Subject;
import com.imuzio.crud2.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<SubjectDto> getSubjects(){
        return subjectRepository.findAll().stream().map(this::subjectDtoBuilder).toList();
    }

    public SubjectDto getSubjectById(Integer id) throws SubjectNotFoundException {
        Optional<Subject> subject = subjectRepository.findById(id);

        if(!subject.isPresent()){
            throw new SubjectNotFoundException("Subject is not available");
        }
        return subjectDtoBuilder(subject.get());
    }

    public Subject save (SubjectDto subjectDto, Integer id) throws DuplicatedNameSubjectException {
        checkName(subjectDto,id);
        Subject subject = subjectBuilder(subjectDto,id);
        return subjectRepository.save(subject);
    }

    public void delete(Integer id){
        subjectRepository.deleteById(id);
    }

    private SubjectDto subjectDtoBuilder(Subject subject){
        SubjectDto subjectDto = new SubjectDto();

        subjectDto.setName(subject.getName());

        return subjectDto;
    }

    private Subject subjectBuilder(SubjectDto subjectDto, Integer id) {
        Subject subject = new Subject();

        subject.setId(id);
        subject.setName(subjectDto.getName());

        return subject;
    }

    private void checkName (SubjectDto subjectDto, Integer id) throws DuplicatedNameSubjectException {
        List <Subject> subjects = subjectRepository.findAll();
        for(Subject subject : subjects){
            if(subjectDto.getName().equals(subject.getName()) && !Objects.equals(id, subject.getId())){
                throw new DuplicatedNameSubjectException("Subject name already in use...");
            }
        }
    }
}
