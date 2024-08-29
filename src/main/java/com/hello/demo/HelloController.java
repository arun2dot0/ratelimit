package com.hello.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {


    @GetMapping("/hello")
    public String consumeMemory(@RequestParam(defaultValue = "Istio") String name ) {
                return "Hello "+name;
    }

}

