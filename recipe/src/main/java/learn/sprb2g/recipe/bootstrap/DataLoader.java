package learn.sprb2g.recipe.bootstrap;

import learn.sprb2g.recipe.domain.*;
import learn.sprb2g.recipe.repositories.CategoryRepository;
import learn.sprb2g.recipe.repositories.RecipeRepository;
import learn.sprb2g.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
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
    public void run(String... args) {
        recipeRepository.saveAll(createRecipes());
    }

    private List<Recipe> createRecipes() {
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
        guacamoleNotes.setRecipe(guacamole);

        addIngredient(guacamole, "Ripe Avocados", 2, each);
        addIngredient(guacamole, "Salt", 0.25, teaspoon);
        addIngredient(guacamole, "Lemon juice", 1, tablespoon);
        addIngredient(guacamole, "Red Onion", 2, tablespoon);
        addIngredient(guacamole, "Serrano Chiles", 2, each);
        addIngredient(guacamole, "Cilantro", 2, tablespoon);
        addIngredient(guacamole, "Black Pepper", 1, dash);
        addIngredient(guacamole, "Ripe Tomato chopped", 0.5, each);

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
        tacosNotes.setRecipe(tacos);

        addIngredient(tacos, "Chili powder", 2, tablespoon);
        addIngredient(tacos, "Dried Oregano", 1, teaspoon);
        addIngredient(tacos, "Dried Cumin", 1, teaspoon);
        addIngredient(tacos, "Sugar", 1, teaspoon);
        addIngredient(tacos, "Slat", 0.5, teaspoon);
        addIngredient(tacos, "Garlic Chopped", 1, each);
        addIngredient(tacos, "Orange zest", 1, tablespoon);
        addIngredient(tacos, "Orange juice", 3, tablespoon);
        addIngredient(tacos, "Olive Oil", 2, tablespoon);
        addIngredient(tacos, "Chicken Thighs", 6, each);

        recipes.add(tacos);

        return recipes;
    }

    private void addIngredient(Recipe recipe, String description, double amount, UnitOfMeasure unit) {
        Ingredient ingredient = new Ingredient(description, amount, unit, recipe);
        recipe.getIngredients().add(ingredient);
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
