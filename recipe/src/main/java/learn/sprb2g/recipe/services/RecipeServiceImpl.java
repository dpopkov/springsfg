package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> findAll() {
        log.info("In RecipeServiceImpl::findAll()");
        Set<Recipe> set = new HashSet<>();
        recipeRepository.findAll().forEach(set::add);
        return set;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> opt = recipeRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new RuntimeException("Recipe Not Found by id: " + id);
    }
}
