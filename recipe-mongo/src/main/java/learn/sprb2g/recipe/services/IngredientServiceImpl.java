package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.commands.IngredientCommand;
import learn.sprb2g.recipe.converters.IngredientCommandToIngredient;
import learn.sprb2g.recipe.converters.IngredientToIngredientCommand;
import learn.sprb2g.recipe.domain.Ingredient;
import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.domain.UnitOfMeasure;
import learn.sprb2g.recipe.repositories.reactive.RecipeReactiveRepository;
import learn.sprb2g.recipe.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeReactiveRepository recipeRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand converter;
    private final IngredientCommandToIngredient toIngredientConverter;

    public IngredientServiceImpl(RecipeReactiveRepository recipeRepository,
                                 UnitOfMeasureReactiveRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand converter,
                                 IngredientCommandToIngredient toIngredientConverter) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.converter = converter;
        this.toIngredientConverter = toIngredientConverter;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return recipeRepository.findById(recipeId)
            .map(recipe -> recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                .findFirst())
            .filter(Optional::isPresent)
            .map(ingredient -> {
                IngredientCommand command = converter.convert(ingredient.get());
                command.setRecipeId(recipeId);
                return command;
            });

        /*
        Optional<Recipe> opt = recipeRepository.findById(recipeId).blockOptional();
        if (opt.isEmpty()) {
            throw new RuntimeException("Recipe not found ID: " + recipeId);
        }
        Recipe recipe = opt.get();
        Optional<Ingredient> first = findIngredientById(ingredientId, recipe);
        if (first.isEmpty()) {
            throw new RuntimeException("Ingredient not found ID: " + ingredientId);
        }
        Ingredient ingredient = first.get();
        IngredientCommand ingredientCommand = converter.convert(ingredient);
        ingredientCommand.setRecipeId(recipeId);
        return Mono.just(ingredientCommand);
         */
    }

    @Override
    @Transactional
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
        String recipeId = command.getRecipeId();
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId).blockOptional();
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
            String unitId = command.getUnit().getId();
            UnitOfMeasure unit = unitOfMeasureRepository.findById(unitId)
                    .blockOptional()
                    .orElseThrow(() -> new RuntimeException("UnitOfMeasure not found for ID " + unitId));
            ingredientFound.setUnit(unit);
        } else {
            // Adding new Ingredient
            Ingredient converted = toIngredientConverter.convert(command);
            recipe.addIngredient(Objects.requireNonNull(converted));
        }
        Recipe savedRecipe = recipeRepository.save(recipe).block();
        Optional<Ingredient> savedIngredientOpt = findIngredientById(command.getId(), savedRecipe);
        if (savedIngredientOpt.isEmpty()) {
            savedIngredientOpt = savedRecipe.getIngredients().stream()
                    .filter(ri -> ri.getDescription().equals(command.getDescription()))
                    .filter(ri -> ri.getAmount().equals(command.getAmount()))
                    .filter(ri -> ri.getUnit().getId().equals(command.getUnit().getId()))
                    .findFirst();
        }
        Ingredient ingredient = savedIngredientOpt.orElseThrow(
                () -> new RuntimeException("Ingredient not saved or not found"));
        IngredientCommand ingredientCommand = converter.convert(ingredient);
        ingredientCommand.setRecipeId(recipeId);
        return Mono.just(ingredientCommand);
    }

    @Override
    @Transactional
    public Mono<Void> deleteByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId).blockOptional();
        if (recipeOpt.isEmpty()) {
            throw new RuntimeException("Cannot find Recipe ID " + recipeId);
        }
        Recipe recipe = recipeOpt.get();
        Optional<Ingredient> ingredientOpt = findIngredientById(ingredientId, recipe);
        if (ingredientOpt.isEmpty()) {
            throw new RuntimeException("Cannot find Ingredient ID " + ingredientId);
        }
        Ingredient toDelete = ingredientOpt.get();
        recipe.getIngredients().remove(toDelete);
        recipeRepository.save(recipe).block();
        log.debug("Deleted Ingredient ID {} in Recipe ID {}", ingredientId, recipeId);
        return Mono.empty();
    }

    private Optional<Ingredient> findIngredientById(String id, Recipe recipe) {
        if (id == null) {
            return Optional.empty();
        }
        return recipe.getIngredients().stream()
                .filter(ing -> ing.getId().equals(id))
                .findFirst();
    }
}
