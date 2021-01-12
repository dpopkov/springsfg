package learn.sprb2g.recipe.controllers;

import learn.sprb2g.recipe.commands.RecipeCommand;
import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.exceptions.NotFoundException;
import learn.sprb2g.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@SuppressWarnings("SameReturnValue")
@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        Recipe recipe = recipeService.findById(Long.valueOf(id));
        model.addAttribute("recipe", recipe);
        return "recipe/show";
    }

    @GetMapping("/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        RecipeCommand recipe = recipeService.findCommandById(id);
        model.addAttribute("recipe", recipe);
        return "recipe/recipeform";
    }

    @PostMapping("/")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand saved = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + saved.getId() + "/show";
    }

    @GetMapping("/{id}/delete")
    public String deleteRecipe(@PathVariable Long id) {
        log.debug("Deleting Recipe ID: " + id);
        recipeService.deleteById(id);
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling Not Found Exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(Exception exception) {
        log.error("Handling Number Format Exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("400error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
