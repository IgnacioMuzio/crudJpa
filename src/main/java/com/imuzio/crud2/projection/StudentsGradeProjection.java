package com.imuzio.crud2.projection;

import org.springframework.beans.factory.annotation.Value;

public interface StudentsGradeProjection {

    @Value("#{target.first_name + ' ' +  target.last_name}")
    String getFirstNameAndLastName();

    @Value("#{target.grade}")
    Float getGrade();
}
