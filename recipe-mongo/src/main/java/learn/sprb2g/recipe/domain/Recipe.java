package learn.sprb2g.recipe.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"categories", "ingredients", "notes"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private Set<Ingredient> ingredients = new HashSet<>();
    private byte[] image = new byte[0];
    private Notes notes;
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
            notes.setRecipe(this);
        }
    }

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        ingredients.add(ingredient);
        return this;
    }
}
