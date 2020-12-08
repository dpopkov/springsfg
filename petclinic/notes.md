# Notes

#### Issue 9: Add WebJars for Bootstrap CSS and JQuery

#### Issue 8: Add Pet Clinic Index Page and Controller

#### Issue 7: Create Pet Clinic Controllers

#### Issue 6: Implement Map Based Services

#### Issue 5: Create Interfaces for Pet Clinic Services

#### Issue 4: Upgrade to JUnit 5

#### Issue 3: Add Maven Release Plugin

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
