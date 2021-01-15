package com.example.demo.exception;

import com.example.demo.payload.ProjectIdExceptionRespone;
import com.example.demo.payload.ProjectNotFoundExceptionRespone;
import com.example.demo.payload.UsernameAlreadyExitstsRespne;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice  //có thể xử lý exception cho cả các method của các class khác, package khác.
@RestController
public class CustomResponeEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler  // xử lí lỗi ngoại lệ cho class đó
    public final ResponseEntity<Object> handlrProjectIdException(ProjectIdException ex, WebRequest request) {
        ProjectIdExceptionRespone exceptionRespone = new ProjectIdExceptionRespone(ex.getMessage());
        return new ResponseEntity(exceptionRespone, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handlrProjectNotFound(ProjectNotFoundException ex, WebRequest request) {
        ProjectNotFoundExceptionRespone exceptionRespone = new ProjectNotFoundExceptionRespone(ex.getMessage());
        return new ResponseEntity(exceptionRespone, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public final ResponseEntity<Object> handlrUsernameArleadyExitsts(UsernameAlreadyExitstsException ex, WebRequest request){
        UsernameAlreadyExitstsRespne responeEx = new UsernameAlreadyExitstsRespne(ex.getMessage());
        return new ResponseEntity<>(responeEx,HttpStatus.BAD_REQUEST);
    }
}

