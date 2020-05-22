package com.ics499.unorater.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
@RestController
public class HelloController {

    @RequestMapping(value = "hello/{name}", method = RequestMethod.GET)
    public String index(@PathVariable String name) {
        return "Hello " + name + ", Greetings from Spring Boot!";
    }

}
