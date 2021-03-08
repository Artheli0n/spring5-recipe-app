package spring.training.personal.recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import spring.training.personal.recipeapp.domain.Recipe;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private RecipeService recipeService;

    public ImageServiceImpl(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    @Transactional
    public void saveImageFile(final Long recipeId, final MultipartFile file) {
        try {

            Recipe recipe = recipeService.findById(recipeId);

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                byteObjects[i++] = b;
            }

            recipe.setImage(byteObjects);

            recipeService.save(recipe);

        } catch(IOException e) {
            log.error("Error occurred", e);

            e.printStackTrace();
        }

    }
}
