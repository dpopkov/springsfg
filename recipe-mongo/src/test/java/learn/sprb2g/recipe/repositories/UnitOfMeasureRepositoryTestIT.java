package learn.sprb2g.recipe.repositories;

import learn.sprb2g.recipe.bootstrap.DataLoader;
import learn.sprb2g.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UnitOfMeasureRepositoryTestIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        recipeRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();
        DataLoader dataLoader = new DataLoader(categoryRepository, unitOfMeasureRepository, recipeRepository);
        dataLoader.run();
    }

    @Test
    void findByDescription() {
        final String description = "Tablespoon";
        Optional<UnitOfMeasure> opt = unitOfMeasureRepository.findByDescription(description);
        assertEquals(description, opt.orElseThrow().getDescription());
    }

    @Test
    void findByDescriptionCup() {
        final String description = "Cup";
        Optional<UnitOfMeasure> opt = unitOfMeasureRepository.findByDescription(description);
        assertEquals(description, opt.orElseThrow().getDescription());
    }
}
