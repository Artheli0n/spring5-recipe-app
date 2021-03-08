package spring.training.personal.recipeapp.repositories;

import spring.training.personal.recipeapp.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
