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
import java.util.Optional;
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
        };
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);

        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isEmpty()){
            throw new RuntimeException("Recipe not Found");
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(final RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);

        if (log.isDebugEnabled()) {
            log.debug("Saved RecipeId:" + savedRecipe.getId());
        }

        return recipeToRecipeCommand.convert(savedRecipe);

    }
}
