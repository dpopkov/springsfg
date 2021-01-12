package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.commands.RecipeCommand;
import learn.sprb2g.recipe.converters.RecipeCommandToRecipe;
import learn.sprb2g.recipe.converters.RecipeToRecipeCommand;
import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.exceptions.NotFoundException;
import learn.sprb2g.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> findAll() {
        log.info("In RecipeServiceImpl::findAll()");
        Set<Recipe> set = new HashSet<>();
        recipeRepository.findAll().forEach(set::add);
        return set;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> opt = recipeRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new NotFoundException("Recipe Not Found by id: " + id);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved Recipe ID: " + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
        log.debug("Deleted Recipe ID: " + id);
    }
}
