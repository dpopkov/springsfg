package learn.sprb2g.reactiveexample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonCommand {
    private String firstName;
    private String lastName;

    public PersonCommand(Person person) {
        firstName = person.getFirstName();
        lastName = person.getLastName();
    }

    public String sayName() {
        return "My name is " + firstName + " " + lastName + ".";
    }
}
