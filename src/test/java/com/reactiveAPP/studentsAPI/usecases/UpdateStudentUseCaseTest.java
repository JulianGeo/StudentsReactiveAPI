package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.domain.collection.Student;
import com.reactiveAPP.studentsAPI.domain.dto.StudentDTO;
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
class UpdateStudentUseCaseTest {

    @Mock
    StudentRepository repoMock;
    ModelMapper mapper;
    UpdateStudentUseCase updateStudentUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        updateStudentUseCase = new UpdateStudentUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("updateStudent_Success")
    void updateStudent(){
        var studentID = "ID1";
        var oldStudent = Mono.just(InstanceProvider.getStudent());
        var newStudent = InstanceProvider.getStudentToUpdate();
        var monoNewStudent = Mono.just(InstanceProvider.getStudentToUpdate());

        Mockito.when(repoMock.findById(ArgumentMatchers.anyString())).thenReturn(oldStudent);
        Mockito.when(repoMock.save(ArgumentMatchers.any(Student.class))).thenReturn(monoNewStudent);

        var service = updateStudentUseCase.apply(studentID,
                mapper.map(newStudent, StudentDTO.class));

        StepVerifier.create(service)
                .expectNextCount(1)
                .verifyComplete();
        Mockito.verify(repoMock).findById(studentID);
        Mockito.verify(repoMock).save(ArgumentMatchers.any(Student.class));
    }

    @Test
    @DisplayName("updateStudent_NonSuccess")
    void updateStudentByNonExistingID(){
        var studentID = "ID1";
        var newCourse = InstanceProvider.getStudentToUpdate();
        var monoNewStudent = Mono.just(InstanceProvider.getStudentToUpdate());

        Mockito.when(repoMock.findById(studentID)).thenReturn(Mono.empty());

        var service = updateStudentUseCase.apply(studentID,
                mapper.map(newCourse, StudentDTO.class));

        StepVerifier.create(service)
                //Test to check that the expected stream is of 0 size
                //.expectNextCount(0)
                //Test to check an expected error
                .expectError(IllegalArgumentException.class)
                .verify();
        Mockito.verify(repoMock).findById(studentID);

        //Mockito.verify(repoMock).save(ArgumentMatchers.any(Student.class));
    }


}