package learn.sprb2g.petclinic.controllers;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.model.Pet;
import learn.sprb2g.petclinic.model.PetType;
import learn.sprb2g.petclinic.services.OwnerService;
import learn.sprb2g.petclinic.services.PetService;
import learn.sprb2g.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.util.Set;

@Controller
@RequestMapping("/owners/{ownerId}/pets")
public class PetController {

    private static final String PETS_CREATE_OR_UPDATE_PET_FORM = "pets/createOrUpdatePetForm";

    private final PetService petService;
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;

    public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    @ModelAttribute("petTypes")
    public Set<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");

        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException{
                setValue(LocalDate.parse(text));
            }
        });
    }

    @GetMapping("/new")
    public String initCreationForm(Owner owner, Model model) {
        Pet pet = Pet.builder().owner(owner).build();
        model.addAttribute(pet);
        return PETS_CREATE_OR_UPDATE_PET_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Pet pet, BindingResult result, Owner owner, Model model) {
        if (ownerHasDuplicatePetName(owner, pet)) {
            result.rejectValue("name", "duplicate", "already exists");
        }
        if (result.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute(pet);
            return PETS_CREATE_OR_UPDATE_PET_FORM;
        }
        owner.addPet(pet);
        petService.save(pet);
        return "redirect:/owners/" + owner.getId();
    }

    private boolean ownerHasDuplicatePetName(Owner owner, @Valid Pet pet) {
        return StringUtils.hasLength(pet.getName())
                && owner.findPetByName(pet.getName(), true) != null;
    }

    @GetMapping("/{petId}/edit")
    public String initUpdateForm(@PathVariable Long petId, Model model) {
        Pet pet = petService.findById(petId);
        model.addAttribute(pet);
        return PETS_CREATE_OR_UPDATE_PET_FORM;
    }

    @PostMapping("/{petId}/edit")
    public String processUpdateForm(@Valid Pet pet, @PathVariable Long petId, BindingResult result, Owner owner, Model model) {
        pet.setId(petId);
        if (ownerHasDuplicatePetName(owner, pet)) {
            result.rejectValue("name", "duplicate", "already exists");
        }
        if (result.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute(pet);
            return PETS_CREATE_OR_UPDATE_PET_FORM;
        }
        Pet found = owner.findPetById(petId);
        // Important:
        // Updating name and birthDate only!
        // The full update operation should be moved to service layer.
        found.setName(pet.getName());
        found.setBirthDate(pet.getBirthDate());
        petService.save(found);
        return "redirect:/owners/" + owner.getId();
    }
}
