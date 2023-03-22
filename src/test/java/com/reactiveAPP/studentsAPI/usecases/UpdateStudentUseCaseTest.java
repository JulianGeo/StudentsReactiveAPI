package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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



}