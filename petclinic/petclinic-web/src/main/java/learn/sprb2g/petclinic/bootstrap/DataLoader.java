package learn.sprb2g.petclinic.bootstrap;

import learn.sprb2g.petclinic.model.*;
import learn.sprb2g.petclinic.services.OwnerService;
import learn.sprb2g.petclinic.services.PetTypeService;
import learn.sprb2g.petclinic.services.SpecialityService;
import learn.sprb2g.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Initializes data on startup of the application.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;

    public DataLoader(OwnerService ownerService, VetService vetService,
                      PetTypeService petTypeService, SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }

    /**
     * Callback used to run the bean.
     */
    @Override
    public void run(String... args) {
        int count = petTypeService.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dogType = savePetType("Dog");
        PetType catType = savePetType("Cat");

        Owner mike = saveOwner("Michael", "Weston", "123 Brickerel", "Miami", "1231231234");
        Pet mikesPet = savePet(dogType, "Rosco", mike, LocalDate.of(2010, 1, 2));
        mike.getPets().add(mikesPet);

        Owner fiona = saveOwner("Fiona", "Glenanne", "321 Brickerel", "Miami", "3213214321");
        Pet fionaPet = savePet(catType, "Tiger", fiona, LocalDate.of(2012, 2, 3));
        fiona.getPets().add(fionaPet);
        System.out.println("Loaded Owners and their Pets...");

        Speciality radiology = saveSpeciality("Radiology");
        Speciality surgery = saveSpeciality("Surgery");
        Speciality dentistry = saveSpeciality("Dentistry");

        Vet same = saveVet("Same", "Axe");
        same.getSpecialities().add(radiology);
        Vet jane = saveVet("Jane", "Doe");
        jane.getSpecialities().add(surgery);
        System.out.println("Loaded Vets and Specialities...");
    }

    private Speciality saveSpeciality(String description) {
        Speciality spec = new Speciality();
        spec.setDescription(description);
        return specialityService.save(spec);
    }

    private Pet savePet(PetType type, String name, Owner owner, LocalDate dob) {
        Pet pet = new Pet();
        pet.setPetType(type);
        pet.setName(name);
        pet.setOwner(owner);
        pet.setBirthDate(dob);
        return pet;
    }

    private PetType savePetType(String name) {
        PetType type = new PetType();
        type.setName(name);
        return petTypeService.save(type);
    }

    private Owner saveOwner(String firstName, String lastName, String address, String city, String telephone) {
        Owner owner = new Owner();
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setAddress(address);
        owner.setCity(city);
        owner.setTelephone(telephone);
        return ownerService.save(owner);
    }

    private Vet saveVet(String firstName, String lastName) {
        Vet vet = new Vet();
        vet.setFirstName(firstName);
        vet.setLastName(lastName);
        return vetService.save(vet);
    }
}
