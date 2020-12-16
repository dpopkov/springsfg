package learn.sprb2g.petclinic.controllers;

import learn.sprb2g.petclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"/vets", "/vets.html", "/vets/index", "/vets/index.html"})
    public String list(Model model) {
        model.addAttribute("vets", vetService.findAll());
        return "vets/index";
    }
}
