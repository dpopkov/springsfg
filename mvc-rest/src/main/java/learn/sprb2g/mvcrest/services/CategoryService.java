package learn.sprb2g.mvcrest.services;

import learn.sprb2g.mvcrest.api.v1.model.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    Optional<CategoryDTO> getCategoryByName(String name);
}
