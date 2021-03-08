package spring.training.personal.recipeapp.services;

import spring.training.personal.recipeapp.commands.IngredientCommand;
import spring.training.personal.recipeapp.commands.UnitOfMeasureCommand;
import spring.training.personal.recipeapp.converters.IngredientCommandToIngredient;
import spring.training.personal.recipeapp.converters.IngredientToIngredientCommand;
import spring.training.personal.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import spring.training.personal.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import spring.training.personal.recipeapp.domain.Ingredient;
import spring.training.personal.recipeapp.domain.Recipe;
import spring.training.personal.recipeapp.domain.UnitOfMeasure;
import spring.training.personal.recipeapp.repositories.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    private static final Long RECIPE_ID = 1L;
    private static final Long INGREDIENT_ID = 2L;

    IngredientService ingredientService;

    @Mock
    RecipeService recipeService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    IngredientRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        ingredientService = new IngredientServiceImpl(recipeService,
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()), unitOfMeasureService,
                ingredientRepository);
    }

    @Test
    void saveIngredient() {
        Ingredient ingredient = new Ingredient();

        when(ingredientRepository.save(any())).thenReturn(ingredient);

        assertNotNull(ingredientService.saveIngredient(ingredient));
        verify(ingredientRepository).save(any());
    }

    @Test
    void findByRecipeIdAndIngredientId() {
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGREDIENT_ID);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(RECIPE_ID, INGREDIENT_ID);

        assertEquals(INGREDIENT_ID, ingredientCommand.getId());
        assertEquals(RECIPE_ID, ingredientCommand.getRecipeId());
        verify(recipeService).findById(anyLong());
    }

    @Test
    void saveIngredientCommandWhenIngredientIsPresent() {
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(1L);
        uomCommand.setDescription("Each");

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(1L);
        uom.setDescription("Each");

        IngredientCommand command = new IngredientCommand();
        command.setId(INGREDIENT_ID);
        command.setRecipeId(RECIPE_ID);
        command.setDescription("MyDescription");
        command.setUom(uomCommand);
        command.setAmount(BigDecimal.valueOf(8));

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
        ingredient.setDescription("blabla");
        ingredient.setAmount(BigDecimal.valueOf(1));

        recipe.addIngredient(ingredient);

        when(recipeService.findById(anyLong())).thenReturn(recipe);
        when(recipeService.save(any())).then(returnsFirstArg());
        when(unitOfMeasureService.findById(anyLong())).thenReturn(uom);

        final IngredientCommand ingredientCommand = ingredientService.saveIngredientCommand(command);

        assertEquals(command.getId(), ingredientCommand.getId());
        assertEquals(command.getDescription(), ingredientCommand.getDescription());
        assertEquals(command.getAmount(), ingredientCommand.getAmount());
        assertEquals(command.getUom().getId(), ingredientCommand.getUom().getId());
        assertEquals(command.getUom().getDescription(), ingredientCommand.getUom().getDescription());

        verify(recipeService).findById(anyLong());
        verify(recipeService).save(any());
    }

    @Test
    void saveIngredientCommandWhenIngredientIsNotPresent() {
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        IngredientCommand command = new IngredientCommand();
        command.setId(INGREDIENT_ID);
        command.setRecipeId(RECIPE_ID);

        when(recipeService.findById(anyLong())).thenReturn(recipe);
        when(recipeService.save(any())).then(returnsFirstArg());
        when(ingredientService.saveIngredient(any())).then(returnsFirstArg());

        final IngredientCommand ingredientCommand = ingredientService.saveIngredientCommand(command);

        assertEquals(command.getId(), ingredientCommand.getId());
        verify(recipeService).findById(anyLong());
        verify(recipeService).save(any());
    }
}
