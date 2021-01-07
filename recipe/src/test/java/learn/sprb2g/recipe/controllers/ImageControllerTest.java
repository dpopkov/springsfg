package learn.sprb2g.recipe.controllers;

import learn.sprb2g.recipe.commands.RecipeCommand;
import learn.sprb2g.recipe.services.ImageService;
import learn.sprb2g.recipe.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    ImageService imageService;
    @InjectMocks
    ImageController imageController;

    private MockMvc mockMvc;
    final Long recipeId = 10L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void testShowImageUploadForm() throws Exception {
        when(recipeService.findCommandById(recipeId)).thenReturn(new RecipeCommand());

        mockMvc.perform(get("/recipe/" + recipeId + "/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/imageuploadform"))
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService).findCommandById(recipeId);
    }

    @Test
    void testHandleImagePost() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework test".getBytes());

        mockMvc.perform(multipart("/recipe/" + recipeId + "/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/" + recipeId + "/show"));
        verify(imageService).saveImageFile(eq(recipeId), any());
    }
}
