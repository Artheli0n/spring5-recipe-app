package spring.training.personal.recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import spring.training.personal.recipeapp.commands.RecipeCommand;
import spring.training.personal.recipeapp.converters.RecipeCommandToRecipe;
import spring.training.personal.recipeapp.converters.RecipeToRecipeCommand;
import spring.training.personal.recipeapp.domain.Recipe;
import spring.training.personal.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(final RecipeRepository recipeRepository,
                             final RecipeCommandToRecipe recipeCommandToRecipe,
                             final RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        if (log.isDebugEnabled()) {
            log.debug("I'm in the Service");
        }
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RuntimeException("Recipe not Found"));
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(final Long id) {
        return recipeToRecipeCommand.convert(this.findById(id));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(final RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);

        if (detachedRecipe == null) {
            throw new RuntimeException("Trying to save or update null recipe");
        }

        Recipe savedRecipe = recipeRepository.save(this.editRecipe(detachedRecipe));

        if (log.isDebugEnabled()) {
            log.debug("Saved RecipeId:" + savedRecipe.getId());
        }

        return recipeToRecipeCommand.convert(savedRecipe);

    }

    @Override
    public void deleteById(final Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Recipe save(final Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    private Recipe editRecipe(final Recipe detachedRecipe) {
        if (detachedRecipe.getId() == null) {
            // new recipe so no editing
            return detachedRecipe;
        }
        Recipe recipeToUpdate = this.findById(detachedRecipe.getId());
        recipeToUpdate.setDescription(detachedRecipe.getDescription());
        recipeToUpdate.setCategories(detachedRecipe.getCategories());
        recipeToUpdate.setPrepTime(detachedRecipe.getPrepTime());
        recipeToUpdate.setCookTime(detachedRecipe.getCookTime());
        recipeToUpdate.setDifficulty(detachedRecipe.getDifficulty());
        recipeToUpdate.setServings(detachedRecipe.getServings());
        recipeToUpdate.setSource(detachedRecipe.getSource());
        recipeToUpdate.setUrl(detachedRecipe.getUrl());
        recipeToUpdate.setDirections(detachedRecipe.getDirections());
        recipeToUpdate.setNotes(detachedRecipe.getNotes());

        return recipeToUpdate;
    }

}
