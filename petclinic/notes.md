# Notes

#### Issue 12: Create Equals method on entity objects

#### Issue 73: Implement Vets JSON Endpoint - CLOSED
 
#### Issue 72: Implement Vets List View - CLOSED 

#### Issue 69: Add ability to add or update a Visit - CLOSED 

#### Issue 68: Add ability to add or update a Pet - CLOSED

#### Issue 67: Add ability to add or update an Owner - CLOSED

#### Issue 66: Implement Find Owner - CLOSED

#### Issue 65: Display Owner Record (HTML) - CLOSED

#### Issue 64: Write Spring MVC Tests for Owner Controller - CLOSED

#### Issue 62: Write CRUD Tests for Owner Spring Data JPA Service - CLOSED

#### Issue 61: Write CRUD Tests for Owner Map Service - CLOSED

#### Issue 63: Add Project Lombok and Refactor - CLOSED

#### Issue 71: Update Profiles to use Spring Data JPA Services - CLOSED

#### Issue 60: Load Visits on Startup in Bootstrap - CLOSED

#### Issue 59: Create Spring Data JPA Service for Visit - CLOSED

#### Issue 58: Create Visit Map Service - CLOSED

#### Issue 49, 50, 51: Create Spring Data JPA Service for PetType, Pet, Vet Speciality - CLOSED

#### Issue 48: Create Spring Data JPA Service for Vets - CLOSED

#### Issue 47: Create Spring Data JPA Service for Owners - CLOSED

#### Issue 46: Add Spring Data JPA Repositories - CLOSED

#### Issue 56: Create Visit JPA Entity - CLOSED

#### Issue 55: Convert Vets to JPA Entities - CLOSED

#### Issue 54: Convert Owners to JPA Entities - CLOSED

#### Issue 53: Update Base Entity with JPA annotation - CLOSED

#### Issue 57: Fix the Menu Links that are Broken - CLOSED

#### Issue 52: Create Specialities, add to Vets on Startup (Bootstrap) - CLOSED

#### Issue 33: Enhance Owners with contact info and Pets on the startup - CLOSED

#### Issue 32: Add PetTypes in with Bootstrap (startup) - CLOSED

#### Issue 29, 31: Create PetType and Specialty Map Service Impl - CLOSED

#### Issue 28: Add Contact Info Properties to Owner - CLOSED

#### Issue 27: Create Vet Speciality Entity, Associate to Vet - CLOSED

#### Issue 26: Create Pet Type, Pet, and Visit Entities - CLOSED

#### Issue 20: Apply Master Layout to Vet List Page - CLOSED

#### Issue 19: Apply Master Layout to Owners List Page - CLOSED

#### Issue 25: Add missing i18n properties files - CLOSED

#### Issue 18: Apply master layout to Index Page - CLOSED

#### Issue 24: Implement WRO4J Maven Plugin and SPC resources - CLOSED

#### Issue 17: Copy Master Template - CLOSED

#### Issue 16: Copy all Static Resources - CLOSED

#### Issue 23: Update Map Impl to manage setting of ID Property - CLOSED

#### Issue 14: List all Vets on Vet Index page - CLOSED
 
#### Issue 15: List all Owners on Owners Index Page - CLOSED 

#### Issue 22: Implement Spring Conf for Services - CLOSED

#### Issue 13: Load Bootstrap Data on Startup - CLOSED

#### Issue 21: Create Owner Index page and controller - CLOSED

#### Issue 7: Create Vet Index page and Controller - CLOSED

#### Issue 8: Add Pet Clinic Index Page and Controller - CLOSED

#### Issue 6: Implement Map Based Services - CLOSED

#### Issue 11: Refactor Service Interfaces to Common Base Interface - CLOSED

#### Issue 10: Add ID to model objects - CLOSED

#### Issue 5: Create Interfaces for Pet Clinic Services - CLOSED

#### Issue 4: Upgrade to JUnit 5

#### Issue 3: Add Maven Release Plugin - CLOSED
* Add `maven-release-plugin` to pom.xml.
* Override goal `install` in the plugin configuration.
* Add `scm` with `developerConnection` to your repo.

#### Issue 2: Implement Pet Clinic POJO Data Model - CLOSED

#### Issue 1: Create Multi-Module Project for Data Model - CLOSED
* Add modules petclinic-data and petclinic-web.
* Move code to modules.
* Move dependencies to pom.xml in modules.
* Add `spring-boot-maven-plugin` config to pom.xml in petclinic-data module.
* Add to pom.xml in petclinic-data module:
```xml
<properties>
    <spring-boot.repackage.skip>true</spring-boot.repackage.skip>
</properties>
```
