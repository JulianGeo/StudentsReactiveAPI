package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.domain.collection.Student;
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
class SaveStudentUseCaseTest {

    @Mock
    StudentRepository repoMock;
    ModelMapper mapper;
    SaveStudentUseCase saveStudentUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        saveStudentUseCase = new SaveStudentUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("saveStudent_Success")
    void SaveCourse(){
        var studentDTO =mapper.map(InstanceProvider.getStudent(), StudentDTO.class);

        Mockito.when(repoMock.save(mapper.map(studentDTO, Student.class)))
                .thenAnswer(invocationOnMock -> Mono.just(invocationOnMock.getArgument(0)));

        var service = saveStudentUseCase.apply(studentDTO);

        StepVerifier.create(service)
                //Test to check next object as a whole
                //.expectNext(mapper.map(InstanceProvider.getStudents().get(0), StudentDTO.class))
                //Custom and specific test for the expected object
                .expectNextMatches(studentDTO1 -> studentDTO1.getLastname().equals("Crespo"))
                //Test to check the size of the stream
                //.expectNextCount(1)
                .verifyComplete();
        Mockito.verify(repoMock).save(mapper.map(studentDTO, Student.class));
    }


}