package com.reactiveAPP.studentsAPI.util;

import com.reactiveAPP.studentsAPI.domain.collection.Student;
import com.reactiveAPP.studentsAPI.domain.course.Course;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InstanceProvider {
    public static List<Student> getStudents(){
        return List.of(
                new Student("Id1", "idNum1", "Elvis", "Crespo", "elvis@gmail.com","basic",new HashSet<>()),
                new Student("Id2", "idNum2", "Elvis", "Presley", "elvis@gmail.com","basic",new HashSet<>()),
                new Student("Id3", "idNum3", "Elvis", "Costello", "elvis@gmail.com","basic",new HashSet<>())
                );
    }

    public static Student getStudent(){
        return new Student("Id1", "idNum1", "Elvis", "Crespo", "elvis@gmail.com","basic",new HashSet<>());
    }

    public static Student getStudentToUpdate(){
        //return new Student("Id3", "idNum3", "Elvis", "Crespo", "elvis@gmail.com","basic",getCourses());
        return null;
    }

    public static Set<Course> getCourses(){
        return Set.of(
                new Course("ID3", "React1","React course" ,"Adryan", "3", new HashSet<>()),
                new Course("ID3", "React2","React course 1" ,"Raul", "3", new HashSet<>()),
                new Course("ID3", "React3","React course 2" ,"Mishel", "5", new HashSet<>())
        );
    }
}
