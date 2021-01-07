package learn.sprb2g.recipe.controllers;

import learn.sprb2g.recipe.commands.RecipeCommand;
import learn.sprb2g.recipe.services.ImageService;
import learn.sprb2g.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("SameReturnValue")
@Slf4j
@Controller
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{id}/image")
    public String showImageUploadForm(@PathVariable Long id, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(id);
        model.addAttribute("recipe", recipeCommand);
        return "/recipe/imageuploadform";
    }

    @PostMapping("/recipe/{id}/image")
    public String handleImagePost(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file) {
        log.debug("Saving image for Recipe ID {}", id);
        imageService.saveImageFile(id, file);
        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("/recipe/{id}/image/view")
    public void renderImageFromDB(@PathVariable Long id, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(id);
        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(recipeCommand.getImage());
        IOUtils.copy(is, response.getOutputStream());
    }
}
