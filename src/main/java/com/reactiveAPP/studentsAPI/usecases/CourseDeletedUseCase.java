package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * Consumer use case to update the students when a course is deleted
 */
/*@Service
@AllArgsConstructor
public class CourseDeletedUseCase implements Function<String, Mono<Void>> {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<Void> apply(String courseID) {
        return studentRepository
                .findAll()
                .filter(student -> student.getCourses().contains(courseID));
    }
}*/
