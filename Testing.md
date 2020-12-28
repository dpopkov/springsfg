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
Placeholder for a table.


JUnit4 vs JUnit5
----------------
| JUnit 4                       | JUnit 5                               |   |
|-------------------------------|---------------------------------------|---|
| @Test(expected = Foo.class    | Assertions.assertThrows(Foo.class)    |   |
| @Test(timeout = 1)            | Assertions.assertTimeout(Duration...) |   |
| @RunWidth(SpringJUnit4ClassRunner.class)  | @ExtendWith(SpringExtension.class) |  @SpringBootTest contains it |
| @Before                       | @BeforeEach   |   |
| @After                        | @AfterEach    |   |
| @BeforeClass                  | @BeforeAll    |   |
| @AfterClass                   | @AfterAll     |   |
| @Ignored                      | @Disabled     |   |
| @Category                     | @Tag          |   |
