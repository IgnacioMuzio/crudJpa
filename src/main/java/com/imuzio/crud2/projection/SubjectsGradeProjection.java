package com.imuzio.crud2.projection;

import org.springframework.beans.factory.annotation.Value;

public interface SubjectsGradeProjection {

    @Value("#{target.name}")
    String getName();
}
