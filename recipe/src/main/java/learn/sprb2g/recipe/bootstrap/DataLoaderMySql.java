package learn.sprb2g.recipe.bootstrap;

import learn.sprb2g.recipe.domain.*;
import learn.sprb2g.recipe.repositories.CategoryRepository;
import learn.sprb2g.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class DataLoaderMySql implements CommandLineRunner {

    private static final List<String> CATEGORIES = List.of(
            "American", "Italian", "Mexican", "Fast Food"
    );
    private static final List<String> UNITS_OF_MEASURE = List.of(
            "Tablespoon", "Cup", "Pinch", "Ounce", "Each", "Pint", "Dash"
    );

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoaderMySql(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        log.info("Loading data for MySQL");
        if (categoryRepository.count() == 0) {
            loadCategories();
        }
        if (unitOfMeasureRepository.count() == 0) {
            loadUnitsOfMeasure();
        }
    }

    private void loadCategories(){
        CATEGORIES.forEach(this::saveCategory);
        log.info("Categories loaded");
    }

    private void loadUnitsOfMeasure(){
        UNITS_OF_MEASURE.forEach(this::saveUnitOfMeasure);
        log.info("UnitOfMeasure loaded");
    }

    private void saveCategory(String description) {
        Category cat1 = new Category();
        cat1.setDescription(description);
        categoryRepository.save(cat1);
    }

    private void saveUnitOfMeasure(String description) {
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription(description);
        unitOfMeasureRepository.save(uom1);
    }
}
