package learn.sprb2g.depinjexamples.controllers;

import learn.sprb2g.depinjexamples.services.GreetingService;

public class PropertyInjectedController {

    public GreetingService greetingService;

    public String getGreeting() {
        return greetingService.sayGreeting();
    }
}
