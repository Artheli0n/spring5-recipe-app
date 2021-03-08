package spring.training.personal.recipeapp.services;

import spring.training.personal.recipeapp.commands.IngredientCommand;
import spring.training.personal.recipeapp.domain.Ingredient;

public interface IngredientService {
    Ingredient saveIngredient(Ingredient ingredient);

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteIngredientById(Long recipeId, Long ingredientId);
}
