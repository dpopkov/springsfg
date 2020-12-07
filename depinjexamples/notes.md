# Notes

## Spring Context

### Dependency Injection - DI
* DI is where a needed dependency is injected by another object.
* The class being injected has no responsibility in instantiating the object being injected.

### Inversion of Control - IoC
* Is a technique to allow dependencies to be injected at runtime.
* Dependencies are not predetermined.

    One important characteristic of a framework is that the methods defined by the user to tailor the 
    framework will often be called within the framework itself, rather than from the user's 
    application code. The framework often plays the role of the main program in coordinating and 
    sequencing application activity. This inversion of control gives frameworks the power to serve as
    extensible skeletons. The methods supplied by the user tailor the generic algorithms defined in 
    the framework for a particular application.  
    (Ralph Johnson and Brian Foote)
    
