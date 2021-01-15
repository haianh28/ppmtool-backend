package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)// bắt trạng thái lỗi ném ra ngoại lệ
public class UsernameAlreadyExitstsException extends RuntimeException{
    public UsernameAlreadyExitstsException(String message) {
        super(message);
    }
}
