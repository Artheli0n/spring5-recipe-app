package spring.training.personal.recipeapp.services;

import spring.training.personal.recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
