package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.repository.StudentRepository;
import com.reactiveAPP.studentsAPI.util.InstanceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteStudentUseCaseTest {

    @Mock
    StudentRepository repoMock;
    ModelMapper mapper;
    DeleteStudentUseCase deleteStudentUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        deleteStudentUseCase = new DeleteStudentUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("deleteStudentByID_Success")
    void deleteStudent(){
        var studentID = "ID1";
        var student = Mono.just(InstanceProvider.getStudent());

        Mockito.when(repoMock.findById(studentID)).thenReturn(student);
        Mockito.when(repoMock.deleteById(studentID)).thenReturn(Mono.empty());

        var service = deleteStudentUseCase.apply(studentID);

        StepVerifier.create(service)
                .expectNextCount(0)
                .verifyComplete();
        Mockito.verify(repoMock).deleteById(studentID);
    }

    @Test
    @DisplayName("deleteStudentByNonExistingID_Unsuccess")
    void deleteCourseUnsuccess(){
        var courseID = "ID1";
        var course = Mono.just(InstanceProvider.getStudent());

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(Mono.empty());

        var service = deleteStudentUseCase.apply(courseID);

        StepVerifier.create(service)
                .expectError(Throwable.class)
                .verify();
        Mockito.verify(repoMock).findById(courseID);
    }
}