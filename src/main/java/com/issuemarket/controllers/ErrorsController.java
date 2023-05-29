package com.issuemarket.controllers;

import com.issuemarket.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.issuemarket.controllers")
public class ErrorsController {

    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, Model model, HttpServletRequest request, HttpServletResponse response){
        int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

        if (e instanceof CommonException){
            CommonException commonException = (CommonException) e;
            status = commonException.getStatus().value();
        }

        response.setStatus(status);
        String URL = request.getRequestURI();

        model.addAttribute("status", status);
        model.addAttribute("path", URL);
        model.addAttribute("message", e.getMessage());
        model.addAttribute("exception", e);

        e.printStackTrace();

        return "error/common";
    }
}