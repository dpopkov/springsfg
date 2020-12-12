package learn.sprb2g.petclinic.bootstrap;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.model.Vet;
import learn.sprb2g.petclinic.services.OwnerService;
import learn.sprb2g.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initializes data on startup of the application.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    /**
     * Callback used to run the bean.
     */
    @Override
    public void run(String... args) {
        saveOwner("Michael", "Weston");
        saveOwner("Fiona", "Glenanne");
        System.out.println("Loaded Owners...");

        saveVet("Same", "Axe");
        saveVet("Jane", "Doe");
        System.out.println("Loaded Vets...");
    }

    private void saveOwner(String firstName, String lastName) {
        Owner owner = new Owner();
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        ownerService.save(owner);
    }

    private void saveVet(String firstName, String lastName) {
        Vet vet = new Vet();
        vet.setFirstName(firstName);
        vet.setLastName(lastName);
        vetService.save(vet);
    }
}
