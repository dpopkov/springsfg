package learn.sprb2g.recipe.domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
public class Notes {

    private String id;
    private Recipe recipe;
    private String recipeNotes;

}
