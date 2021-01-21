package learn.sprb2g.petclinic.controllers;

import learn.sprb2g.petclinic.model.Pet;
import learn.sprb2g.petclinic.model.Visit;
import learn.sprb2g.petclinic.services.PetService;
import learn.sprb2g.petclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/owners/*/pets/{petId}/visits")
public class VisitController {

    private static final String PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    /** Called before each handler method. */
    @ModelAttribute("visit")
    public Visit createNewVisitForExistingPet(@PathVariable Long petId, Model model) {
        Pet pet = petService.findById(petId);
        model.addAttribute(pet);
        Visit visit = new Visit();
        pet.addVisit(visit);
        return visit;
    }

    // Spring MVC calls method createNewVisitForExistingPet(..) before initNewVisitForm()
    @GetMapping("/new")
    public String initNewVisitForm() {
        return PETS_CREATE_OR_UPDATE_VISIT_FORM;
    }

    // Spring MVC calls method createNewVisitForExistingPet(..) before processNewVisitForm(..)
    @PostMapping("/new")
    public String processNewVisitForm(@Valid Visit visit, Pet pet, BindingResult result) {
        if (result.hasErrors()) {
            return PETS_CREATE_OR_UPDATE_VISIT_FORM;
        }
        visitService.save(visit);
        Long ownerId = pet.getOwner().getId();
        return "redirect:/owners/" + ownerId;
    }
}
