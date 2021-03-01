package spring.training.personal.recipeapp.repositories;

import spring.training.personal.recipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
