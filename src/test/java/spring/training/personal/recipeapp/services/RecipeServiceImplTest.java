package spring.training.personal.recipeapp.services;

import spring.training.personal.recipeapp.domain.Recipe;
import spring.training.personal.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getRecipes() {

        Recipe recipe = new Recipe();
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }
}
