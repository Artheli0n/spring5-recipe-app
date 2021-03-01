package spring.training.personal.recipeapp.repositories;

import spring.training.personal.recipeapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
