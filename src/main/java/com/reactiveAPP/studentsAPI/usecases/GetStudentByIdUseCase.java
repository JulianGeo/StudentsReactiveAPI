package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.domain.dto.StudentDTO;
import com.reactiveAPP.studentsAPI.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class GetStudentByIdUseCase implements Function<String, Mono<StudentDTO>> {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<StudentDTO> apply(String ID) {
        return this.studentRepository
                .findById(ID)
                .switchIfEmpty(Mono.empty())
                .map(student -> mapper.map(student, StudentDTO.class));
    }

}
