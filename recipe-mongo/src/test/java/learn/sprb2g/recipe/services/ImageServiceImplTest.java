package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.domain.Recipe;
import learn.sprb2g.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    ImageServiceImpl imageService;

    @Test
    void testSaveImageFile() throws IOException {
        // Given
        MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "Spring Framework test".getBytes());
        final String recipeId = "10";
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        // When
        imageService.saveImageFile(recipeId, multipartFile);

        // Then
        verify(recipeRepository).save(argumentCaptor.capture());
        Recipe saved = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, saved.getImage().length);
    }
}
