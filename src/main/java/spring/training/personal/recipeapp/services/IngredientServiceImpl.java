package spring.training.personal.recipeapp.services;

import spring.training.personal.recipeapp.commands.IngredientCommand;
import spring.training.personal.recipeapp.converters.IngredientCommandToIngredient;
import spring.training.personal.recipeapp.converters.IngredientToIngredientCommand;
import spring.training.personal.recipeapp.domain.Ingredient;
import spring.training.personal.recipeapp.domain.Recipe;
import spring.training.personal.recipeapp.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeService recipeService;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureService unitOfMeasureService;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(
            final RecipeService recipeService,
            final IngredientToIngredientCommand ingredientToIngredientCommand,
            final IngredientCommandToIngredient ingredientCommandToIngredient,
            final UnitOfMeasureService unitOfMeasureService,
            final IngredientRepository ingredientRepository) {
        this.recipeService = recipeService;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureService = unitOfMeasureService;
        this.ingredientRepository = ingredientRepository;
    }


    @Override
    public Ingredient saveIngredient(final Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(final Long recipeId, final Long ingredientId) {

        Recipe recipe = recipeService.findById(recipeId);

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();

        if (ingredientCommandOptional.isEmpty()) {
            throw new RuntimeException("Ingredient not found for id: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(final IngredientCommand command) {
        Recipe recipe = recipeService.findById(command.getRecipeId());

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

        Ingredient ingredient;

        if (ingredientOptional.isPresent()) {
            // changes will be saved when saving the recipe (due to cascade)
            ingredient = ingredientOptional.get();
            ingredient.setAmount(command.getAmount());
            ingredient.setDescription(command.getDescription());
            ingredient.setUom(unitOfMeasureService.findById(command.getUom().getId()));
        } else {
            // will create the id of the ingredient
            ingredient = this.saveIngredient(ingredientCommandToIngredient.convert(command));
            recipe.addIngredient(ingredient);
        }

        recipeService.save(recipe);

        return ingredientToIngredientCommand.convert(ingredient);
    }

    @Override
    public void deleteIngredientById(final Long recipeId, final Long ingredientId) {
        Recipe recipe = recipeService.findById(recipeId);
        recipe.getIngredients().removeIf(ingredient -> ingredient.getId().equals(ingredientId));
        recipeService.save(recipe);
    }
}
