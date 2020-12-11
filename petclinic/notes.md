# Notes

#### Issue 9: Add WebJars for Bootstrap CSS and JQuery

#### Issue 12: Create Equals method on entity objects

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
