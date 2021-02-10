package learn.sprb2g.mvcrest.controllers.v1;

import learn.sprb2g.mvcrest.api.v1.model.CategoryDTO;
import learn.sprb2g.mvcrest.api.v1.model.CategoryListDTO;
import learn.sprb2g.mvcrest.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories/";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());
    }

    @GetMapping("{categoryName}")
    public ResponseEntity<CategoryDTO> getByName(@PathVariable String categoryName) {
        Optional<CategoryDTO> dtoOptional = categoryService.getCategoryByName(categoryName);
        return dtoOptional.map(categoryDTO -> new ResponseEntity<>(categoryDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
