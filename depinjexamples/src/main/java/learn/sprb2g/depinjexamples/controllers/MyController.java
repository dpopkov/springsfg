package learn.sprb2g.depinjexamples.controllers;

import learn.sprb2g.depinjexamples.services.GreetingService;
import org.springframework.stereotype.Controller;

@Controller
public class MyController {

    private final GreetingService greetingService;

    public MyController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayHello() {
        System.out.println(greetingService.sayGreeting());
        return "Hi Folks!";
    }
}
