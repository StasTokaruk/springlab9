package com.labrob9.springlab9.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Обробка IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("address","/booking");
        return "error-page"; // Ім'я HTML-сторінки для показу помилки
    }

    // Обробка 404 помилки (опціонально)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle404(NoHandlerFoundException ex, Model model) {
        model.addAttribute("errorMessage", "Page not found.");
        model.addAttribute("address","/home");
        return "error-page";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handle400(MissingServletRequestParameterException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("address","/hotels");
        return "error-page";
    }
}
