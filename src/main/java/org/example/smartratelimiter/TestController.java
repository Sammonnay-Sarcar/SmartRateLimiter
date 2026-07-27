package org.example.smartratelimiter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("helloworld")
public class TestController {
    @GetMapping("/")
    public String getHelloWorld(){   
        return "Hello World";
    }
}
