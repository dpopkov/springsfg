package learn.sprb2g.recipe.repositories;

import learn.sprb2g.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Disabled("Not fixed for MongoDB yet")
@DataJpaTest
class UnitOfMeasureRepositoryTestIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

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
