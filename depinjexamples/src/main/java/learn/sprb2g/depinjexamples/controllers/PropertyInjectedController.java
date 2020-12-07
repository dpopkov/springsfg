package learn.sprb2g.depinjexamples.controllers;

import learn.sprb2g.depinjexamples.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class PropertyInjectedController {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection") // this field injection is just an example
    @Autowired
    @Qualifier("propertyInjectedGreetingService")
    public GreetingService greetingService;

    public String getGreeting() {
        return greetingService.sayGreeting();
    }
}
