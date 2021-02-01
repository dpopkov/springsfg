package learn.sprb2g.recipe.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@EqualsAndHashCode
public class Notes {

    @Id
    private String id;
    private String recipeNotes;

}
