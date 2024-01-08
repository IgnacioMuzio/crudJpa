package com.imuzio.crud2.model.entity.projection;

import org.springframework.beans.factory.annotation.Value;

public interface StudentsGradeProjection {

    @Value("#{target.firstName + ' ' +  target.lastName}")
    String getFirstNameAndLastName();

    @Value("#{grade}")
    Float getGrade();
}
