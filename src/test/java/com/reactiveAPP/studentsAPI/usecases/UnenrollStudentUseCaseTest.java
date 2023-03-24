package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.domain.collection.Student;
import com.reactiveAPP.studentsAPI.publisher.StudentPublisher;
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
class UnenrollStudentUseCaseTest {

    @Mock
    StudentRepository repoMock;
    ModelMapper mapper;
    UnenrollStudentUseCase unenrollStudentUseCase;

    @Mock
    StudentPublisher studentPublisher;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        unenrollStudentUseCase = new UnenrollStudentUseCase(repoMock, mapper, studentPublisher);
    }


    @Test
    @DisplayName("updateStudent_Success")
    void updateStudent(){
        var studentID = "ID1";
        var monoStudent = Mono.just(InstanceProvider.getStudent());
        var student = InstanceProvider.getStudent();
        var course = InstanceProvider.getCourse();

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString()))
                .thenReturn(monoStudent);
        Mockito.when(repoMock.save(ArgumentMatchers.any(Student.class)))
                .thenReturn(monoStudent);

        var service = unenrollStudentUseCase
                .apply(studentID,course);

        StepVerifier.create(service)
                .expectNextMatches(
                        studentDTO -> studentDTO.getLastname().equals(
                                InstanceProvider
                                        .getStudent()
                                        .getLastname()))
                .verifyComplete();

        Mockito.verify(repoMock).findById(studentID);
        Mockito.verify(repoMock).save(ArgumentMatchers.any(Student.class));
    }
}