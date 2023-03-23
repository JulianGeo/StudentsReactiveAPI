package com.reactiveAPP.studentsAPI.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reactiveAPP.studentsAPI.domain.course.Course;
import com.reactiveAPP.studentsAPI.domain.dto.StudentDTO;
import com.reactiveAPP.studentsAPI.publisher.StudentPublisher;
import com.reactiveAPP.studentsAPI.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.function.BiFunction;

@Service
@AllArgsConstructor
public class UnenrollStudentUseCase implements BiFunction<String, Course, Mono<StudentDTO>> {
    private final StudentRepository studentRepository;

    private final ModelMapper mapper;

    private final StudentPublisher studentPublisher;


    @Override
    public Mono<StudentDTO> apply(String studentID, Course course){

        return this.studentRepository
                .findById(studentID)
                .switchIfEmpty(Mono.error(new Throwable("Student not found")))
                .flatMap(student -> {
                    Set<Course> courses = student.getCourses();
                    /*if (!courses.contains(course)){
                        return Mono.error(new Throwable("Student is not enrolled yet"));
                    }*/
                    courses.remove(course);
                    student.setCourses(courses);
                    return this.studentRepository.save(student);})
                .map(student1 -> mapper.map(student1, StudentDTO.class))
                .doOnSuccess(studentDTO1 -> {
                    try {
                        studentPublisher.publish(studentDTO1, course.getId(), "unenroll");
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });

    }
}
