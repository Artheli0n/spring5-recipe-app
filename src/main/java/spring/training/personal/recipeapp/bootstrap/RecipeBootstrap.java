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

        guacRecipe.addIngredient(buildIngredient("Ripe avocados", new BigDecimal(2), eachMeasure));
        guacRecipe.addIngredient(buildIngredient("Kosher salt", new BigDecimal(".5"), teaSpoonMeasure));
        guacRecipe.addIngredient(
                buildIngredient("Fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonMeasure));
        guacRecipe.addIngredient(
                buildIngredient("Minced red onion or thinly sliced green onion", new BigDecimal(2), eachMeasure));
        guacRecipe.addIngredient(buildIngredient("Cilantro", new BigDecimal(2), tableSpoonMeasure));
        guacRecipe.addIngredient(buildIngredient("Freshly grated black pepper", new BigDecimal(2), dashMeasure));
        guacRecipe.addIngredient(buildIngredient("Ripe tomato, seeds and pulp removed, chopped", new BigDecimal(
                ".5"), eachMeasure));

        guacRecipe.addCategory(americanCategory);
        guacRecipe.addCategory(mexicanCategory);

        Notes tacosNotes = new Notes();
        tacosNotes.setRecipeNotes("notes here...");

        Recipe tacosRecipe = buildRecipe("Spicy Grilled Chicken Taco", 20, 9, Difficulty.MODERATE, "directions here...",
                tacosNotes);

        tacosRecipe.addIngredient(buildIngredient("Ancho chili powder", new BigDecimal(2), tableSpoonMeasure));
        tacosRecipe.addIngredient(buildIngredient("Dried oregano", new BigDecimal(1), teaSpoonMeasure));
        tacosRecipe.addIngredient(buildIngredient("Dried Cumin", new BigDecimal(1), teaSpoonMeasure));
        tacosRecipe.addIngredient(buildIngredient("Sugar", new BigDecimal(1), teaSpoonMeasure));
        tacosRecipe.addIngredient(buildIngredient("Salt", new BigDecimal(".5"), teaSpoonMeasure));
        tacosRecipe.addIngredient(buildIngredient("Clove of garlic, chopped", new BigDecimal(1), eachMeasure));
        tacosRecipe.addIngredient(buildIngredient("Finely grated orange zest", new BigDecimal(1), tableSpoonMeasure));
        tacosRecipe.addIngredient(buildIngredient("Fresh-squeezed orange juice", new BigDecimal(3), tableSpoonMeasure));
        tacosRecipe.addIngredient(buildIngredient("Olive oil", new BigDecimal(2), tableSpoonMeasure));
        tacosRecipe.addIngredient(buildIngredient("Boneless chicken thighs", new BigDecimal(4), eachMeasure));
        tacosRecipe.addIngredient(buildIngredient("Small corn toritllas", new BigDecimal(8), eachMeasure));
        tacosRecipe.addIngredient(buildIngredient("Packed baby arugula", new BigDecimal(3), cupMeasure));
        tacosRecipe.addIngredient(buildIngredient("Medium ripe avocados, slic", new BigDecimal(2), eachMeasure));
        tacosRecipe.addIngredient(buildIngredient("Radishes, thinly sliced", new BigDecimal(4), eachMeasure));
        tacosRecipe.addIngredient(buildIngredient("Cherry tomatoes, halved", new BigDecimal(".5"), pintMeasure));
        tacosRecipe.addIngredient(buildIngredient("Red onion, thinly sliced", new BigDecimal(".25"), eachMeasure));
        tacosRecipe.addIngredient(buildIngredient("Roughly chopped cilantro", new BigDecimal(4), eachMeasure));
        tacosRecipe.addIngredient(
                buildIngredient("Cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupMeasure));
        tacosRecipe.addIngredient(buildIngredient("Lime, cut into wedges", new BigDecimal(4), eachMeasure));

        tacosRecipe.addCategory(americanCategory);
        tacosRecipe.addCategory(mexicanCategory);

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

    private Ingredient buildIngredient(final String description, final BigDecimal amount, final UnitOfMeasure uom) {
        return new Ingredient(description, amount, uom);
    }
}
