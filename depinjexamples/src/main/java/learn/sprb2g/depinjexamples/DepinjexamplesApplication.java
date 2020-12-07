package learn.sprb2g.depinjexamples;

import learn.sprb2g.depinjexamples.controllers.ConstructorInjectedController;
import learn.sprb2g.depinjexamples.controllers.MyController;
import learn.sprb2g.depinjexamples.controllers.PropertyInjectedController;
import learn.sprb2g.depinjexamples.controllers.SetterInjectedController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DepinjexamplesApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(DepinjexamplesApplication.class, args);

        MyController myController = (MyController) ctx.getBean("myController");
        String greeting = myController.sayHello();
        System.out.println(greeting);

        System.out.println("----------- Property");
        PropertyInjectedController propertyInjectedController =
                (PropertyInjectedController) ctx.getBean("propertyInjectedController");
        System.out.println(propertyInjectedController.getGreeting());

        System.out.println("----------- Setter");
        SetterInjectedController setterInjectedController =
                (SetterInjectedController) ctx.getBean("setterInjectedController");
        System.out.println(setterInjectedController.getGreeting());

        System.out.println("----------- Constructor");
        ConstructorInjectedController constructorInjectedController =
                (ConstructorInjectedController) ctx.getBean("constructorInjectedController");
        System.out.println(constructorInjectedController.getGreeting());
    }

}
