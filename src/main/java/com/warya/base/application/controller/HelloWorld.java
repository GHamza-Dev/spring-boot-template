package com.warya.base.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("${api.hello-wolrd}")
public class HelloWorld {


    @GetMapping
    public ResponseEntity<Map<String, String>> helloWorld() {
        return new ResponseEntity<>(Map.of("message", "Hello World"), HttpStatus.OK);
    }
}
