package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteStudentUseCase implements Function<String, Mono<Void>> {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<Void> apply(String Id) {
        return this.studentRepository
                .findById(Id)
                .switchIfEmpty(Mono.error(new Throwable("Student not found")))
                .flatMap(student -> this.studentRepository.deleteById(Id))
                //TODO: fix it to catch the error
                .onErrorResume(throwable -> Mono.error(throwable));
    }
}

