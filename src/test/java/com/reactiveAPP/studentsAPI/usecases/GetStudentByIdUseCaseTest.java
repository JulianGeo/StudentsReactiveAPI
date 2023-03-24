package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.domain.dto.StudentDTO;
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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetStudentByIdUseCaseTest {

    @Mock
    StudentRepository repoMock;
    ModelMapper mapper;
    GetStudentByIdUseCase getStudentByIdUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getStudentByIdUseCase = new GetStudentByIdUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("getStudentById_Success")
    void getStudentById(){
        var studentID = "ID1";
        var student = Mono.just(InstanceProvider.getStudent());

        Mockito.when(repoMock.findById(studentID)).thenReturn(student);

        var service = getStudentByIdUseCase.apply(studentID);

        StepVerifier.create(service)
                //.expectNextCount(1)
                .expectNext(mapper.map(InstanceProvider.getStudents().get(0), StudentDTO.class))
                .verifyComplete();
        Mockito.verify(repoMock).findById(studentID);
    }

    @Test
    @DisplayName("getStudentById_NonSuccess")
    void getStudentByNonExistingID(){
        var studentID = "ID1";

        Mockito.when(repoMock.findById(studentID)).thenReturn(Mono.empty());

        var service = getStudentByIdUseCase.apply(studentID);

        StepVerifier.create(service)
                .expectErrorMessage("Student not found")
                .verify();
        Mockito.verify(repoMock).findById(studentID);
    }

}