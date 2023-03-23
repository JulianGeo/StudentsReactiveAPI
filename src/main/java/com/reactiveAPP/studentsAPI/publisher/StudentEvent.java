package com.reactiveAPP.studentsAPI.publisher;

import com.reactiveAPP.studentsAPI.domain.dto.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class StudentEvent {
        private StudentDTO studentDTO;
        private String courseID;
        private String eventType;
}
