package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.domain.collection.Student;
import com.reactiveAPP.studentsAPI.domain.dto.StudentDTO;
import com.reactiveAPP.studentsAPI.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
@AllArgsConstructor
public class UpdateStudentUseCase implements BiFunction<String, StudentDTO, Mono<StudentDTO>> {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<StudentDTO> apply(String id, StudentDTO studentDTO) {
        return this.studentRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("No student matches the provided ID")))
                .flatMap(student ->{
                    studentDTO.setId(student.getId());
                    return studentRepository.save(mapper.map(studentDTO, Student.class));
                }).map(course -> mapper.map(course, StudentDTO.class))
                .onErrorResume(Mono::error);
    }
}
