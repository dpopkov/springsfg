package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.commands.IngredientCommand;
import learn.sprb2g.recipe.converters.IngredientCommandToIngredient;
import learn.sprb2g.recipe.converters.IngredientToIngredientCommand;
import learn.sprb2g.recipe.domain.Ingredient;
import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.domain.UnitOfMeasure;
import learn.sprb2g.recipe.repositories.RecipeRepository;
import learn.sprb2g.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand converter;
    private final IngredientCommandToIngredient toIngredientConverter;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand converter,
                                 IngredientCommandToIngredient toIngredientConverter) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.converter = converter;
        this.toIngredientConverter = toIngredientConverter;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> opt = recipeRepository.findById(recipeId);
        if (opt.isEmpty()) {
            throw new RuntimeException("Recipe not found ID: " + recipeId);
        }
        Recipe recipe = opt.get();
        Optional<Ingredient> first = findIngredientById(ingredientId, recipe);
        if (first.isEmpty()) {
            throw new RuntimeException("Ingredient not found ID: " + ingredientId);
        }
        return converter.convert(first.get());
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Long recipeId = command.getRecipeId();
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
        if (recipeOpt.isEmpty()) {
            log.error("Recipe not found for ID {}", recipeId);
            throw new RuntimeException("Recipe not found for ID " + recipeId);
        }
        Recipe recipe = recipeOpt.get();
        Optional<Ingredient> opt = findIngredientById(command.getId(), recipe);
        if (opt.isPresent()) {
            Ingredient ingredientFound = opt.get();
            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setAmount(command.getAmount());
            Long unitId = command.getUnit().getId();
            UnitOfMeasure unit = unitOfMeasureRepository.findById(unitId)
                    .orElseThrow(() -> new RuntimeException("UnitOfMeasure not found for ID " + unitId));
            ingredientFound.setUnit(unit);
        } else {
            // Adding new Ingredient
            Ingredient converted = toIngredientConverter.convert(command);
            recipe.addIngredient(Objects.requireNonNull(converted));
        }
        Recipe savedRecipe = recipeRepository.save(recipe);
        return converter.convert(findIngredientById(command.getId(), savedRecipe).orElseThrow(
                () -> new RuntimeException("Saved Ingredient not found for ID " + command.getId()))
        );
    }

    private Optional<Ingredient> findIngredientById(Long id, Recipe recipe) {
        if (id == null) {
            return Optional.empty();
        }
        return recipe.getIngredients().stream()
                .filter(ing -> ing.getId().equals(id))
                .findFirst();
    }
}
