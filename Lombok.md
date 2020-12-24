About Lombok
------------

### How Lombok works
* Hooks in via the Annotation processor API.
* The raw source code is passed to Lombok for code generation before the Java compiler continues.
* Produces properly compiled Java code in conjunction with the Java compiler.
* You need to verify that you have enabled the "Annotation Processing" under Compiler settings in Intellij Idea.

### Lombok Features

* val - local variables declared final.
* var - mutable local variables.
* @NonNull - null check, throws npe.
* @Cleanup - call close() on resource in finally block.
* @Getters, @Setters.
* @Getter(lazy=true) - calculates an expensive value once and caches it.
* @ToString - generates toString of classname and fields, field names (opt), super toString (opt).
* @EqualsAndHashCode -  by default uses non-static, not-transient fields, can exclude properties.
* @NoArgsConstructor.
* @RequiredArgsConstructor - for all final or marked @NonNull fields.
* @AllArgsConstructor.
* @Data - boilerplate for POJOs, combines @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor.
* @Value - immutable variant of @Data.
* @Builder - implements the pattern.
* @SneakyThrows - throw checked exceptions without declaring them.
* @Synchronized - safer synchronized.
* @Log - creates a java.util.logging.Logger.
* @Slf4j - creates an SLF4J logger.

### Adding Lombok, IDE Configuration.

* Add Maven dependency (the version is curated by Spring Boot starter).
* Add Lombok plug-in to Intellij Idea.