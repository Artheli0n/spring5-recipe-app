package spring.training.personal.recipeapp.services;

import lombok.extern.slf4j.Slf4j;
import spring.training.personal.recipeapp.domain.Recipe;
import spring.training.personal.recipeapp.repositories.RecipeRepository;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(final RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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
}
