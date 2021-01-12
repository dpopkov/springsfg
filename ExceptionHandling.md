Exception Handling in Spring MVC
================================

HTTP Status Codes
-----------------

* HTTP 5XX Server Error
    * HTTP 500 - Internal Server Error
        * Generally, any unhandled exception
    * Other 500 errors are generally not used with Spring MVC
* HTTP 4XX Client Errors - Generally Checked Exceptions
    * 400 Bad Request - Cannot process due to client error
    * 401 Unauthorized - Authentication required
    * 404 Not Found - Resource Not Found
    * 405 Method Not Allowed - HTTP method not allowed
    * 409 Conflict - Possible with simultaneous updates
    * 417 Expectation Failed - Sometimes used with RESTful interfaces
    * 418 - I'm a Teapot - April Fools Joke from IETF in 1998


@ResponseStatus
---------------
* Allows annotating custom exception classes to indicate to the framework 
the HTTP status you want returned when that exception is thrown.
* Global to the application.


@ExceptionHandler
-----------------
* Works at the controller level
* Allows defining custom exception handling
    * Can be used with @ResponseStatus for returning an HTTP status
    * Can be used to return a specific view
    * Can take total control and work with the Model and View
        * 'Model' cannot be a parameter of an ExceptionHandler method


HandlerExceptionResolver
------------------------
* HandlerExceptionResolver isn an interface you can implement 
for custom exception handling
* Used internally by Spring MVC
* Note: Model is not passed into it

Internal Spring MVC Exception Handlers
--------------------------------------
* Spring MVC has 3 implementations of HandlerExceptionResolver
* ExceptionHandlerExceptionResolver - Matches uncaught exceptions to @ExceptionHandler
* ResponseStatusExceptionResolver - Looks for uncaught exceptions matching @ResponseStatus
* DefaultHandlerExceptionResolver - Converts standard Spring Exceptions to HTTP status codes (Internal to Spring MVC)

Custom HandlerExceptionResolver
-------------------------------
* You can provide your own implementations of HandlerExceptionResolver
* Typically implemented with Spring's Ordered interface to define order the handlers will run in
* Custom implementations are uncommon due to Spring robust exception handling

SimpleMappingExceptionResolver
------------------------------
* A Spring Bean you can define to map exceptions to specific views
* You only define the exception class name (no package) and the view name
* You can optionally define a default error page

Which to Use When
-----------------
* If just setting the HTTP status - use @ResponseStatus
* If redirection to a view - use SimpleMappingExceptionResolver
* If both, consider @ExceptionHandler on the controller
