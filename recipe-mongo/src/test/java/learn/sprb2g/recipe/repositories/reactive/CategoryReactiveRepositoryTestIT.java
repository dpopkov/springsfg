package learn.sprb2g.recipe.repositories.reactive;

import learn.sprb2g.recipe.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class CategoryReactiveRepositoryTestIT {

    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Test
    void testFindByDescription() {
        Category category = new Category();
        category.setDescription("Reactive");
        categoryReactiveRepository.save(category).then().block();

        Category fetched = categoryReactiveRepository.findByDescription("Reactive").block();
        assertNotNull(fetched);
        assertNotNull(fetched.getId());
        assertEquals("Reactive", fetched.getDescription());
    }
}
