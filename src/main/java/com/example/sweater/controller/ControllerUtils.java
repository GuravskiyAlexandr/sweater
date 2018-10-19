package com.example.sweater.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
    static Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> result = new HashMap<>();
//        Collector<FieldError, ?, Map<String, String>> errorMapCollector = Collectors.toMap(
//                fieldError -> fieldError.getField() + "Error",
//                FieldError::getDefaultMessage );
//
//        System.out.println(errorMapCollector);
//        return bindingResult.getFieldErrors().stream().collect(errorMapCollector);

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            if (!result.containsKey(fieldError.getField() + "Error")) {
                result.put(fieldError.getField() + "Error", fieldError.getDefaultMessage());
            }
        }
        return result;
    }
}
