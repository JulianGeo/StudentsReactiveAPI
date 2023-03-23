package com.reactiveAPP.studentsAPI.router;

import com.reactiveAPP.studentsAPI.domain.collection.Student;
import com.reactiveAPP.studentsAPI.domain.course.Course;
import com.reactiveAPP.studentsAPI.domain.dto.StudentDTO;
import com.reactiveAPP.studentsAPI.repository.StudentRepository;
import com.reactiveAPP.studentsAPI.usecases.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
@AllArgsConstructor
public class StudentRouter {

    private WebClient courseAPI;

    public StudentRouter(){
        courseAPI = WebClient.create("http://localhost:8081");
    }

    @Bean
    public RouterFunction<ServerResponse> getAllStudents(GetAllStudentsUseCase getAllStudentsUseCase){
        return route(GET("/api/students"),
                request -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllStudentsUseCase.get(), StudentDTO.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> getStudentById(GetStudentByIdUseCase getStudentByIdUseCase){
        return route(GET("/api/students/{id}"),
                request -> getStudentByIdUseCase.apply(request.pathVariable("id"))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(courseDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(courseDTO))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> saveStudent(SaveStudentUseCase saveStudentUseCase){
        return route(POST("/api/students").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(StudentDTO.class)
                        .flatMap(courseDTO -> saveStudentUseCase.apply(courseDTO)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> updateStudent(UpdateStudentUseCase updateStudentUseCase){
        return route(PUT("/api/students/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(StudentDTO.class)
                        .flatMap(courseDTO -> updateStudentUseCase.apply(request.pathVariable("id"), courseDTO)
                                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> enrollStudent(EnrollStudentUseCase enrollStudentUseCase){
        return route(PUT("api/students/{id_e}/enroll/{id_c}"),
                request ->
                        courseAPI.get()
                                .uri("/api/courses/"+request.pathVariable("id_c"))
                                .retrieve()
                                .bodyToMono(Course.class)
                                .switchIfEmpty(Mono.error(new Throwable("Course not found")))
                                .flatMap(course -> enrollStudentUseCase
                                        .apply(request.pathVariable("id_e"),course)
                                        .flatMap(studentDTO -> ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(studentDTO))
                                        .onErrorResume(throwable -> ServerResponse.badRequest().build())));
        //.onErrorResume(throwable -> ServerResponse.badRequest().bodyValue(StudentDTO.class)));

    }

    @Bean
    public RouterFunction<ServerResponse> unenrollStudent(UnenrollStudentUseCase unenrollStudentUseCase){
        return route(PUT("api/students/{id_e}/unenroll/{id_c}"),
                request ->
                        courseAPI.get()
                                .uri("/api/courses/"+request.pathVariable("id_c"))
                                .retrieve()
                                .bodyToMono(Course.class)
                                .switchIfEmpty(Mono.error(new Throwable("Course not found")))
                                .flatMap(course -> unenrollStudentUseCase
                                        .apply(request.pathVariable("id_e"),course)
                                        .flatMap(studentDTO -> ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(studentDTO))
                                        .onErrorResume(throwable -> ServerResponse.badRequest().build())));
        //.onErrorResume(throwable -> ServerResponse.badRequest().bodyValue(StudentDTO.class)));

    }

    @Bean
    public RouterFunction<ServerResponse> deleteStudentById(DeleteStudentUseCase deleteStudentUseCase){
        return route(DELETE("api/students/{id}"),
                request -> deleteStudentUseCase.apply(request.pathVariable("id"))
                        .thenReturn(ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("Student with ID: "+request.pathVariable("id") +", was deleted"))
                        .flatMap(serverResponseMono -> serverResponseMono)
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }



}
