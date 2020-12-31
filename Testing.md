About Testing
=============

Testing Terminology
-------------------

* __Code under Test__ - This is the code (or application) you are testing.
* __Test Fixture__ - "a fixed state of a set of objects used as a baseline for running tests. The purpose of a test fixture is to ensure that there is a well known and fixed environment in which tests are run so that resuls are repeatable." - JUnit doc.
    * Includes: input data, mock objects, loading database with known data, etc.
* __Unit Tests / Unit Testing__ - code written to test code under test:
    * Designed to test specific sections of code
    * Percentage of lines of code tested is code coverage
        * Ideal coverage is in the 70-80% range
    * Should be 'unity' and execute very fast
    * Should have no external dependencies
        * i.e. no database, no Spring context, etc
* __Integration Tests__ - designed to test behaviors between objects and parts of the overal system
    * Much larger scope
    * Can include the Spring Context, database, and message brokers
    * Will run much slower than unit tests
* __Functional Tests__ - typically means you are testing the running application:
    * Application is live, likely deployed in a known environment
    * Functional touch points are tested
        * i.e. Using a web driver, calling web services, sending/receiving messages etc
* __TDD__ - Test Driven Development - Write tests first, which will fail, then code to fix tests.
* __BDD__ - Behavior Driven Development - Builds on TDD and specifies that tests of any unit of sotware should be specified in terms of desired behavior of the unit.
    * Often implemented with DSLs to create natural language tests.
    * JBehave, Cucumber, Spock.
    * Example: given, when, then.
* __Mock__ - a fake implementation of a class used for testing.
* __Spy__ - a partial mock, allowing you to override select methods of a real class.


Testing Goals
-------------
* Generally, you will want the majority of your tests to be unit tests
* Bringing up the Spring Context makes your tests exponentially slower
* Try to test specific business logic in unit tests
* Use Integration Tests to test interactions
* Think of a pyramid. Base is unit test, middle is integration tests, top is functional tests. 


Test Scope Dependencies
-----------------------
* Using spring-boot-starter-test (default from Spring Initializr) will load the following dependencies:
    * JUnit - the de-facto standard for unit testing
    * Spring Test and Spring Boot Test - utilities and integration test support for Spring Boot applications
    * AssertJ - a fluent assertion library
    * Hamcrest - a library of matcher objects
    * Mockito - a Java mocking framework
    * JSONassert - an assertion library for JSON
    * JSONPath - XPath for JSON
    
    
Spring Boot Annotations
-----------------------
| Annotation            | Description               |
|-----------------------|---------------------------|
| @RunWith(SpringRunner.class) - if JUnit ver<5         | Run test with Spring Context  |
| @ExtendWith(SpringExtension.class) - if JUnit ver==5  | Run test with Spring Context  |
| @SpringBootTest       | Search for Spring Boot Application for configuration  |
| @TestConfiguration    | Specify a Spring configuration for your test          |
| @MockBean             | Injects Mockito Mock          |
| @SpyBean              | Inject Mockito Spy            |
| @JsonTest             | Creates a Jackson or Gson object mapper via Spring Boot   |
| @WebMvcTest           | Used to test web context without a full http server       |
| @DataJpaTest          | Used to test data layer with embedded database            |
| @JdbcTest             | Like @DataJpaTest, but does not configure entity manager  |
| @DataMongoTest        | Configures an embedded MongoDB tor testing                |
| @RestClientTest       | Creates a mock server for testing rest clients            |
| @AutoConfigureRestDocs    | Allows you to use Spring Rest Docs in tests, creating API documentation   |
| @BootStrapWith        | Used to configure how the TestContext is bootstrapped                 |
| @ContextConfiguration | Used to direct Spring how to configure the context for the test       |
| @ContextHierarchy     | Allows you to create a context hierarchy with @ContextConfiguration   |
| @ActiveProfiles       | Set which Spring Profiles are active for the test             | 
| @TestPropertySource   | Configure the property sources for the test                   |
| @DirtiesContext       | Resets the Spring Context after the test (expensive to do)    |
| @WebAppConfiguration  | Indicates Spring should use a Web Application context         |
| @TestExecutionListeners   | Allows you to specify listeners for testing events        |
| @Transactional        | Run test in transaction, rollback when complete by default    |
| @BeforeTransaction    | Action to run before starting a transaction                   |
| @AfterTransaction     | Action to run after a transaction                             |
| @Commit               | Specifies the transaction should be committed after the test  |
| @Rollback             | Transaction should be rolled back after test (default action) |
| @Sql                  | Specify SQL scripts to run before                             |
| @SqlConfig            | Define meta data for SQL scripts                              |
| @SqlGroup             | Group of @Sql annotations                                     |
| @Repeat               | Repeat test x number of times                                 |
| @Timed                | Similar to JUnit timeout, but will wait for test to complete, unlike JUnit    |
| @IfProfileValue       | Indicates test is enabled for a specific testing environment  |
| @ProfileValueSourceConfiguration  | Specify a profile value source                    | 

JUnit4 vs JUnit5
----------------
| JUnit 4                       | JUnit 5                               |   |
|-------------------------------|---------------------------------------|---|
| @Test(expected = Foo.class)   | Assertions.assertThrows(Foo.class)    |   |
| @Test(timeout = 1)            | Assertions.assertTimeout(Duration...) |   |
| @RunWidth(SpringJUnit4ClassRunner.class)  | @ExtendWith(SpringExtension.class) |  @SpringBootTest contains it |
| @Before                       | @BeforeEach   |   |
| @After                        | @AfterEach    |   |
| @BeforeClass                  | @BeforeAll    |   |
| @AfterClass                   | @AfterAll     |   |
| @Ignore                       | @Disabled     |   |
| @Category                     | @Tag          |   |
