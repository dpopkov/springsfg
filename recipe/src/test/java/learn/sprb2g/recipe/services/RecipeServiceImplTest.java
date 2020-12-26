package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @AfterEach
    void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testFindAll() {
        var data = List.of(
                Recipe.builder().id(1L).build(),
                Recipe.builder().id(2L).build());
        when(recipeRepository.findAll()).thenReturn(data);
        assertEquals(2, recipeService.findAll().size());
        verify(recipeRepository, times(1)).findAll();
    }
}
