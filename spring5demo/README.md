# Spring 5 Demo

Simple demo web application using in-memory database

Done:
1. Add JPA entities, establish many-to-many relationships.
2. Add repositories extending `org.springframework.data.repository.CrudRepository`.
3. Add class BootstrapData which implements `org.springframework.boot.CommandLineRunner`.
4. Add Publisher entity and repository.
5. Add and configure Spring MVC Controller: annotate with `@Controller`, map methods with `@RequestMapping`.
6. Add Thymeleaf Spring Boot starter dependency: `spring-boot-starter-thymeleaf`
7. Add Thymeleaf templates, use `th:each` and `th:text` attributes.
