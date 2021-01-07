package learn.sprb2g.recipe.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public void saveImageFile(Long id, MultipartFile file) {
        // todo: write test and implement
        log.debug("Received a file to save");
    }
}
