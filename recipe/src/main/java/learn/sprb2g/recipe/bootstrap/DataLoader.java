package learn.sprb2g.recipe.bootstrap;

import learn.sprb2g.recipe.domain.*;
import learn.sprb2g.recipe.repositories.CategoryRepository;
import learn.sprb2g.recipe.repositories.RecipeRepository;
import learn.sprb2g.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Profile("default")
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public DataLoader(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                      RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (recipeRepository.count() == 0) {
            recipeRepository.saveAll(createRecipes());
        }
    }

    private List<Recipe> createRecipes() {
        log.info("In DataLoader::createRecipes()");
        List<Recipe> recipes = new ArrayList<>(2);

        UnitOfMeasure each = unitByDescription("Each");
        UnitOfMeasure teaspoon = unitByDescription("Teaspoon");
        UnitOfMeasure tablespoon = unitByDescription("Tablespoon");
        UnitOfMeasure dash = unitByDescription("Dash");
        UnitOfMeasure pint = unitByDescription("Pint");
        UnitOfMeasure cup = unitByDescription("Cup");

        Category mexican = categoryByDescription("Mexican");
        Category american = categoryByDescription("American");

        Recipe guacamole = new Recipe();
        guacamole.getCategories().add(mexican);
        guacamole.getCategories().add(american);
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setServings(2);
        guacamole.setSource("www.simplyrecipes.com");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. " +
                "Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. " +
                "(See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "... more items");
        guacamole.setDifficulty(Difficulty.EASY);
        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("Be careful handling chiles if using. Wash your hands thoroughly after handling " +
                "and do not touch your eyes or the area near your eyes with your hands for several hours.");
        guacamole.setNotes(guacamoleNotes);

        guacamole.addIngredient(new Ingredient("Ripe Avocados", 2, each));
        guacamole.addIngredient(new Ingredient("Salt", 0.25, teaspoon));
        guacamole.addIngredient(new Ingredient("Lemon juice", 1, tablespoon));
        guacamole.addIngredient(new Ingredient("Red Onion", 2, tablespoon));
        guacamole.addIngredient(new Ingredient("Serrano Chiles", 2, each));
        guacamole.addIngredient(new Ingredient("Cilantro", 2, tablespoon));
        guacamole.addIngredient(new Ingredient("Black Pepper", 1, dash));
        guacamole.addIngredient(new Ingredient("Ripe Tomato chopped", 0.5, each));

        recipes.add(guacamole);

        Recipe tacos = new Recipe();
        tacos.getCategories().add(mexican);
        tacos.getCategories().add(american);
        tacos.setDescription("Spicy Grilled Chicken Taco");
        tacos.setCookTime(9);
        tacos.setPrepTime(20);
        tacos.setDifficulty(Difficulty.MODERATE);
        tacos.setSource("www.simplyrecipes.com");
        tacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacos.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, " +
                "cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. " +
                "Add the chicken to the bowl and toss to coat all over. " +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "... more items");
        Notes tacosNotes = new Notes();
        tacosNotes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, " +
                "on buy it online.");
        tacos.setNotes(tacosNotes);

        tacos.addIngredient(new Ingredient("Chili powder", 2, tablespoon));
        tacos.addIngredient(new Ingredient("Dried Oregano", 1, teaspoon));
        tacos.addIngredient(new Ingredient("Dried Cumin", 1, teaspoon));
        tacos.addIngredient(new Ingredient("Sugar", 1, teaspoon));
        tacos.addIngredient(new Ingredient("Slat", 0.5, teaspoon));
        tacos.addIngredient(new Ingredient("Garlic Chopped", 1, each));
        tacos.addIngredient(new Ingredient("Orange zest", 1, tablespoon));
        tacos.addIngredient(new Ingredient("Orange juice", 3, tablespoon));
        tacos.addIngredient(new Ingredient("Olive Oil", 2, tablespoon));
        tacos.addIngredient(new Ingredient("Chicken Thighs", 6, each));

        recipes.add(tacos);

        return recipes;
    }

    private Category categoryByDescription(String description) {
        return categoryRepository.findByDescription(description).orElseThrow(
                () -> new RuntimeException("Expected Category Not Found: " + description));
    }

    private UnitOfMeasure unitByDescription(String description) {
        return unitOfMeasureRepository.findByDescription(description).orElseThrow(
                () -> new RuntimeException("Expected UOM Not Found: " + description));
    }
}
