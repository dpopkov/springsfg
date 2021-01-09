package learn.sprb2g.petclinic.controllers;

import learn.sprb2g.petclinic.model.Owner;
import learn.sprb2g.petclinic.services.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("SameReturnValue")
@Slf4j
@RequestMapping("/owners")
@Controller
public class OwnerController {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        // Preventing the Model from getting the id
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping({"/index", "/index.html"})
    public String list(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {
        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        Owner owner = ownerService.findById(ownerId);
        modelAndView.addObject(owner);
        return modelAndView;
    }

    @GetMapping("/find")
    public String initFindOwnersForm(Model model) {
        model.addAttribute(new Owner());
        return "owners/findOwners";
    }

    @GetMapping("")
    public String processFindOwnersForm(Owner owner, BindingResult result, Model model) {
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }
        String searchPattern = "%" + owner.getLastName() + "%";
        List<Owner> found = ownerService.findAllByLastNameLike(searchPattern);
        if (found.isEmpty()) {
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (found.size() == 1) {
            return "redirect:/owners/" + found.get(0).getId();
        } else {
            model.addAttribute("selections", found);
            return "owners/ownersList";
        }
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute(new Owner());
        return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
        }
        Owner saved = ownerService.save(owner);
        log.debug("Saved Owner ID {}", saved.getId());
        return "redirect:/owners/" + saved.getId();
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable Long ownerId,  Model model) {
        Owner owner = ownerService.findById(ownerId);
        model.addAttribute(owner);
        return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable Long ownerId) {
        if (result.hasErrors()) {
            return OWNERS_CREATE_OR_UPDATE_OWNER_FORM;
        }
        owner.setId(ownerId);
        Owner updated = ownerService.save(owner);
        log.debug("Updated Owner ID {}", updated.getId());
        return "redirect:/owners/" + updated.getId();
    }
}
