Database initialization with Spring
-----------------------------------


### Hibernate DDL Auto

* DDL = Data Definition Language.
* DML = Data Manipulation Language.
* Hibernate property is set by the Spring property `spring.jpa.hibernate.ddl-auto`.
* Options are: none, validate, update, create, create-drop.
* Spring Boot will use create-drop for embedded databases (hsql, h2, derby) or none.


### Initialize with Hibernate

* Data can be loaded from `import.sql`
    * Hibernate feature (not Spring specific)
    * Must be on root of class path
    * Only executed if Hibernate's `ddl-auto` property is set to `create` or `create-drop`


### Spring JDBC

* Spring's DataSource initializer via Spring Boot will by default load `schema.sql` and `data.sql` from the root of the classpath.
* Spring Boot will also load platform specific sql from `schema-${platform}.sql` and `data-${platform}.sql`.
    * You must set `spring.datasource.platform`.
* It may conflict with Hibernate's DDL auto property.
    * Should use setting of `none` or `validate`.

