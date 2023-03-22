package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.repository.StudentRepository;
import com.reactiveAPP.studentsAPI.util.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllStudentsUseCaseTest {

    @Mock
    StudentRepository repoMock;
    ModelMapper mapper;
    GetAllStudentsUseCase getAllStudentsUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getAllStudentsUseCase = new GetAllStudentsUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("getAllStudents_Success")
    void getAllStudents(){
        var fluxStudents = Flux.just(
                InstanceProvider.getStudents().get(0),
                InstanceProvider.getStudents().get(1),
                InstanceProvider.getStudents().get(2));

        Mockito.when(repoMock.findAll()).thenReturn(fluxStudents);

        var service = getAllStudentsUseCase.get();

        StepVerifier.create(service)
                .expectNextCount(3)
                .verifyComplete();
        Mockito.verify(repoMock).findAll();
    }

}