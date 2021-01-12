package learn.sprb2g.recipe.controllers;

import learn.sprb2g.recipe.commands.IngredientCommand;
import learn.sprb2g.recipe.commands.RecipeCommand;
import learn.sprb2g.recipe.commands.UnitOfMeasureCommand;
import learn.sprb2g.recipe.services.IngredientService;
import learn.sprb2g.recipe.services.RecipeService;
import learn.sprb2g.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@SuppressWarnings("SameReturnValue")
@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String list(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredient list for Recipe ID: {}", recipeId);
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipeCommand);
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String ingredientId, Model model) {
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(
                Long.valueOf(recipeId), Long.valueOf(ingredientId));
        model.addAttribute("ingredient", ingredientCommand);
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newRecipeIngredient(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setRecipeId(recipeCommand.getId());
        ingredient.setUnit(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredient);
        Set<UnitOfMeasureCommand> uomAll = unitOfMeasureService.findAll();
        model.addAttribute("uomAll", uomAll);
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        IngredientCommand ingredient = ingredientService.findByRecipeIdAndIngredientId(
                Long.valueOf(recipeId), Long.valueOf(ingredientId));
        model.addAttribute("ingredient", ingredient);
        Set<UnitOfMeasureCommand> uomAll = unitOfMeasureService.findAll();
        model.addAttribute("uomAll", uomAll);
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand saved = ingredientService.saveIngredientCommand(command);
        log.debug("Saved Recipe ID {}", saved.getRecipeId());
        log.debug("Saved Ingredient ID {}", saved.getId());
        return "redirect:/recipe/" + saved.getRecipeId() + "/ingredient/" + saved.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId) {
        log.debug("Deleting Ingredient ID {} in Recipe ID {}", ingredientId, recipeId);
        ingredientService.deleteByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
