package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long id, MultipartFile file) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(id);
        if (recipeOpt.isEmpty()) {
            throw new RuntimeException("Cannot find Recipe ID " + id);
        }
        Recipe recipe = recipeOpt.get();
        try {
            recipe.setImage(file.getBytes());
            recipeRepository.save(recipe);
            log.debug("Saved Image file for Recipe ID {}", id);
        } catch (IOException e) {
            throw new RuntimeException("Error saving image bytes", e);
        }
    }
}
