package learn.sprb2g.petclinic.bootstrap;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.model.Vet;
import learn.sprb2g.petclinic.services.OwnerService;
import learn.sprb2g.petclinic.services.VetService;
import learn.sprb2g.petclinic.services.map.OwnerServiceMap;
import learn.sprb2g.petclinic.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Initializes data on startup of the application.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader() {
        this.ownerService = new OwnerServiceMap();
        this.vetService = new VetServiceMap();
    }

    /**
     * Callback used to run the bean.
     */
    @Override
    public void run(String... args) {
        saveOwner(1L, "Michael", "Weston");
        saveOwner(2L, "Fiona", "Glenanne");
        System.out.println("Loaded Owners...");

        saveVet(1L, "Same", "Axe");
        saveVet(2L, "Jane", "Doe");
        System.out.println("Loaded Vets...");
    }

    private void saveOwner(Long id, String firstName, String lastName) {
        Owner owner = new Owner();
        owner.setId(id);
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        ownerService.save(owner);
    }

    private void saveVet(Long id, String firstName, String lastName) {
        Vet vet = new Vet();
        vet.setId(id);
        vet.setFirstName(firstName);
        vet.setLastName(lastName);
        vetService.save(vet);
    }
}
