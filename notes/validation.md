Data Validation
===============

* JSR 303 Java Bean Validation
* JSR 349 Bean Validation 1.1
* JSR 380 Bean Validation 2.0


Built in Constraint Definitions
-------------------------------

* __@Null__ - Checks value is null
* __@NotNull__ - Checks value is not null
* __@AssertTrue__ - Value is true
* __@AssertFalse__ - Value is false
* __@Min__ - Number is equal or higher
* __@Max__ - Number is equal or less
* __@DecimalMin__ - Value is larger
* __@DecimalMax__ - Value is less than
* __@Negative__ - Value is less than zero. Zero invalid.
* __@NegativeOrZero__
* __@Positive__ - Value is greater than zero. Zero invalid
* __@PositiveOrZero__
* __@Size__ - checks if string or collection is between a min and max
* __@Digits__ - check for integer digits and fraction digits
* __@Past__ - checks if date is in past
* __@PastOrPresent__ - Checks if date is in past or present
* __@Future__ - checks if date is in future
* __@FutureOrPresent__ - checks if date is present or in future
* __@Pattern__ - checks against RegEx pattern
* __@NotEmpty__ - checks if value is null or empty (whitespace characters or empty collection)
* __@NonBlank__ - checks string is not null or not whitespace characters
* __@Email__ - checks if string value is an email address


Hibernate Validator Constraints
-------------------------------

* __@ScriptAssert__ - class level annotation, checks class against script
* __@CreditCardNumber__ - verifies value is a credit card number
* __@Currency__ - valid currency amount
* __@DurationMax__ - duration less than given value
* __@DurationMin__ - duration greater than given value
* __@EAN__ - valid EAN barcode
* __@ISBN__ - valid ISBN value
* __@Length__ - string length between given min and max
* __@CodePointLength__ - validates that code point length of the character sequence is between min and max included
* __@LuhnCheck__ - Luhn check sum
* __@Mod10Check__ - Mod 10 check sum
* __@Mod11Check__ - Mod 11 check sum
* __@Range__ - checks if number is between given min and max (inclusive)
* __@SafeHtml__ - checks for safe HTML
* __@UniqueElements__ - checks if collection has unique elements
* __@Url__ - checks for valid URL
