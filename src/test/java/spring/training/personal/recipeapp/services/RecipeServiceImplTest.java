package spring.training.personal.recipeapp.services;

import spring.training.personal.recipeapp.commands.RecipeCommand;
import spring.training.personal.recipeapp.converters.CategoryCommandToCategory;
import spring.training.personal.recipeapp.converters.CategoryToCategoryCommand;
import spring.training.personal.recipeapp.converters.IngredientCommandToIngredient;
import spring.training.personal.recipeapp.converters.IngredientToIngredientCommand;
import spring.training.personal.recipeapp.converters.NotesCommandToNotes;
import spring.training.personal.recipeapp.converters.NotesToNotesCommand;
import spring.training.personal.recipeapp.converters.RecipeCommandToRecipe;
import spring.training.personal.recipeapp.converters.RecipeToRecipeCommand;
import spring.training.personal.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import spring.training.personal.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import spring.training.personal.recipeapp.domain.Recipe;
import spring.training.personal.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
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
        recipeService = new RecipeServiceImpl(recipeRepository,
                new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                        new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                        new NotesCommandToNotes()), new RecipeToRecipeCommand(new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new NotesToNotesCommand()));
    }

    @Test
    public void getRecipeByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull(recipeReturned, "Null recipe returned");
        verify(recipeRepository).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void getRecipesTest() {

        Recipe recipe = new Recipe();
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteById() throws Exception {
        Long idToDelete = 2L;
        recipeService.deleteById(idToDelete);

        verify(recipeRepository).deleteById(anyLong());
    }

    @Test
    void save() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeRepository.save(any())).thenReturn(recipe);

        assertEquals(recipe.getId(), recipeService.save(recipe).getId());
        verify(recipeRepository).save(any());
    }

    @Test
    void findCommandById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeReturned = recipeService.findCommandById(1L);

        assertNotNull(recipeReturned);
        assertEquals(recipe.getId(), recipeReturned.getId());
        verify(recipeRepository).findById(anyLong());
    }

    @Test
    void saveRecipeCommand() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeRepository.save(any())).then(returnsFirstArg());
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        assertEquals(recipeCommand.getId(), recipeService.saveRecipeCommand(recipeCommand).getId());
        verify(recipeRepository).save(any());
    }
}
