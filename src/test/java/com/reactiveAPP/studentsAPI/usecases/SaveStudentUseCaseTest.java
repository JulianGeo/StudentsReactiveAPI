package com.reactiveAPP.studentsAPI.usecases;

import com.reactiveAPP.studentsAPI.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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

}