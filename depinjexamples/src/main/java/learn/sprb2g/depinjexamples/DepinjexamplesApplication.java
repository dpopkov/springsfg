package learn.sprb2g.depinjexamples;

import learn.sprb2g.depinjexamples.controllers.*;
import learn.sprb2g.depinjexamples.examplebeans.FakeDataSource;
import learn.sprb2g.depinjexamples.examplebeans.FakeJmsBroker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DepinjexamplesApplication {

    public static void main(String[] args) {
//        runDiExamples(args);

        runExternalPropertiesExample(args);
    }

    private static void runExternalPropertiesExample(String[] args) {
        ApplicationContext ctx = SpringApplication.run(DepinjexamplesApplication.class, args);
        FakeDataSource dataSource = ctx.getBean(FakeDataSource.class);
        System.out.println("User: " + dataSource.getUser());
        System.out.println("Password: " + dataSource.getPassword());
        System.out.println("Url: " + dataSource.getUrl());

        FakeJmsBroker jmsBroker = ctx.getBean(FakeJmsBroker.class);
        System.out.println("JMS Username: " + jmsBroker.getUsername());
        System.out.println("JMS Password: " + jmsBroker.getPassword());
        System.out.println("JMS Url: " + jmsBroker.getUrl());
    }

    @SuppressWarnings("unused")
    private static void runDiExamples(String[] args) {
        ApplicationContext ctx = SpringApplication.run(DepinjexamplesApplication.class, args);

        System.out.println("----------- Which Pet is the Best");
        PetController petController = ctx.getBean("petController", PetController.class);
        System.out.println(petController.whichPetIsTheBest());

        System.out.println("----------- I18n");
        I18nController i18nController = (I18nController) ctx.getBean("i18nController");
        System.out.println(i18nController.sayHello());

        System.out.println("----------- Primary");
        MyController myController = (MyController) ctx.getBean("myController");
        System.out.println(myController.sayHello());

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
