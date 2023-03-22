package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.domain.dto.StudentDTO;
import com.reactiveAPP.studentsAPI.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class GetAllStudentsUseCase implements Supplier<Flux<StudentDTO>> {


    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    @Override
    public Flux<StudentDTO> get() {
        return this.studentRepository
                .findAll()
                .switchIfEmpty(Mono.error(new Throwable("No students available")))
                .map(student -> mapper.map(student, StudentDTO.class))
                .onErrorResume(Mono::error);
    }

}
