package learn.sprb2g.mvcrest.api.v1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;

    public CategoryDTO(String name) {
        this.name = name;
    }
}
