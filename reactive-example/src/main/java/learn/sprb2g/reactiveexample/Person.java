package learn.sprb2g.reactiveexample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String firstName;
    private String lastName;

    public String sayName() {
        return "My name is " + firstName + " " + lastName + ".";
    }
}
