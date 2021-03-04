package spring.training.personal.recipeapp.services;

import spring.training.personal.recipeapp.commands.RecipeCommand;
import spring.training.personal.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand findCommandById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    void deleteById(Long id);
}
