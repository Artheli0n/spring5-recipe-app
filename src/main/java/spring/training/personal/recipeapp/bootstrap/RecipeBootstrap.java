package spring.training.personal.recipeapp.bootstrap;

import spring.training.personal.recipeapp.domain.Category;
import spring.training.personal.recipeapp.domain.Ingredient;
import spring.training.personal.recipeapp.domain.Notes;
import spring.training.personal.recipeapp.domain.Recipe;
import spring.training.personal.recipeapp.domain.UnitOfMeasure;
import spring.training.personal.recipeapp.domain.enums.Difficulty;
import spring.training.personal.recipeapp.repositories.CategoryRepository;
import spring.training.personal.recipeapp.repositories.RecipeRepository;
import spring.training.personal.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(final RecipeRepository recipeRepository,
                           final CategoryRepository categoryRepository,
                           final UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        UnitOfMeasure eachMeasure = getUnitOfMeasure("Each");
        UnitOfMeasure tableSpoonMeasure = getUnitOfMeasure("Tablespoon");
        UnitOfMeasure teaSpoonMeasure = getUnitOfMeasure("Teaspoon");
        UnitOfMeasure dashMeasure = getUnitOfMeasure("Dash");
        UnitOfMeasure pintMeasure = getUnitOfMeasure("Pint");
        UnitOfMeasure cupMeasure = getUnitOfMeasure("Cup");

        Category americanCategory = getCategory("American");
        Category mexicanCategory = getCategory("Mexican");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("notes here...");

        Recipe guacRecipe = buildRecipe("Perfect Guacamole", 10, 0, Difficulty.EASY, "directions here...", guacNotes);
        guacNotes.setRecipe(guacRecipe);

        guacRecipe.getIngredients().add(buildIngredient("Ripe avocados", new BigDecimal(2), eachMeasure, guacRecipe));
        guacRecipe.getIngredients()
                .add(buildIngredient("Kosher salt", new BigDecimal(".5"), teaSpoonMeasure, guacRecipe));
        guacRecipe.getIngredients()
                .add(buildIngredient("Fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonMeasure,
                        guacRecipe));
        guacRecipe.getIngredients()
                .add(buildIngredient("Minced red onion or thinly sliced green onion", new BigDecimal(2), eachMeasure,
                        guacRecipe));
        guacRecipe.getIngredients().add(buildIngredient("Cilantro", new BigDecimal(2), tableSpoonMeasure, guacRecipe));
        guacRecipe.getIngredients()
                .add(buildIngredient("Freshly grated black pepper", new BigDecimal(2), dashMeasure, guacRecipe));
        guacRecipe.getIngredients().add(buildIngredient("Ripe tomato, seeds and pulp removed, chopped", new BigDecimal(
                ".5"), eachMeasure, guacRecipe));

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);

        Notes tacosNotes = new Notes();
        tacosNotes.setRecipeNotes("notes here...");

        Recipe tacosRecipe = buildRecipe("Spicy Grilled Chicken Taco", 20, 9, Difficulty.MODERATE, "directions here...",
                tacosNotes);
        tacosNotes.setRecipe(tacosRecipe);

        tacosRecipe.getIngredients()
                .add(buildIngredient("Ancho chili powder", new BigDecimal(2), tableSpoonMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Dried oregano", new BigDecimal(1), teaSpoonMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Dried Cumin", new BigDecimal(1), teaSpoonMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(buildIngredient("Sugar", new BigDecimal(1), teaSpoonMeasure, tacosRecipe));
        tacosRecipe.getIngredients().add(buildIngredient("Salt", new BigDecimal(".5"), teaSpoonMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Clove of garlic, chopped", new BigDecimal(1), eachMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Finely grated orange zest", new BigDecimal(1), tableSpoonMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Fresh-squeezed orange juice", new BigDecimal(3), tableSpoonMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Olive oil", new BigDecimal(2), tableSpoonMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Boneless chicken thighs", new BigDecimal(4), eachMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Small corn toritllas", new BigDecimal(8), eachMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Packed baby arugula", new BigDecimal(3), cupMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Medium ripe avocados, slic", new BigDecimal(2), eachMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Radishes, thinly sliced", new BigDecimal(4), eachMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Cherry tomatoes, halved", new BigDecimal(".5"), pintMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Red onion, thinly sliced", new BigDecimal(".25"), eachMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Roughly chopped cilantro", new BigDecimal(4), eachMeasure, tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupMeasure,
                        tacosRecipe));
        tacosRecipe.getIngredients()
                .add(buildIngredient("Lime, cut into wedges", new BigDecimal(4), eachMeasure, tacosRecipe));

        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);

        recipes.add(guacRecipe);
        recipes.add(tacosRecipe);

        return recipes;
    }

    private UnitOfMeasure getUnitOfMeasure(final String description) {
        return unitOfMeasureRepository.findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Expected UOM to be Found"));
    }

    private Category getCategory(final String description) {
        return categoryRepository.findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Expected Category to be Found"));
    }

    private Recipe buildRecipe(final String description, final Integer prepTime, final Integer cookTime, final
    Difficulty difficulty, final String directions, final Notes notes) {
        Recipe recipe = new Recipe();
        recipe.setDescription(description);
        recipe.setPrepTime(prepTime);
        recipe.setCookTime(cookTime);
        recipe.setDifficulty(difficulty);
        recipe.setDirections(directions);
        recipe.setNotes(notes);

        return recipe;
    }

    private Ingredient buildIngredient(final String description, final BigDecimal amount, final UnitOfMeasure uom,
                                       final Recipe recipe) {
        return new Ingredient(description, amount, uom, recipe);
    }
}
