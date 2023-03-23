package com.reactiveAPP.studentsAPI.domain.course;

import com.reactiveAPP.studentsAPI.domain.collection.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    private String id;
    private String name;
    private String description;
    private String coach;
    private String level;
    private Set<String> studentsID=new HashSet<>();
}
