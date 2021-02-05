package learn.sprb2g.recipe.repositories.reactive;

import learn.sprb2g.recipe.domain.UnitOfMeasure;
import learn.sprb2g.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UnitOfMeasureReactiveRepositoryTestIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @BeforeEach
    void clearRepository() {
        unitOfMeasureRepository.deleteAll();
    }

    @Test
    void testSave() {
        UnitOfMeasure unit = new UnitOfMeasure();
        unit.setDescription("Mongo");
        unitOfMeasureReactiveRepository.save(unit).block();
        Long count = unitOfMeasureReactiveRepository.count().block();
        assertNotNull(count);
        assertEquals(Long.valueOf(1L), count);

        Flux<UnitOfMeasure> all = unitOfMeasureReactiveRepository.findAll();
        UnitOfMeasure found = all.blockFirst();
        assertNotNull(found);
        assertNotNull(found.getId());
        assertEquals("Mongo", found.getDescription());
    }
}
