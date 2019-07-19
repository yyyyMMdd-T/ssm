package com.qf.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

       @ExceptionHandler(Exception.class)

        public String ex(Exception ex){
           ex.printStackTrace();

           return "error";
        }

}
