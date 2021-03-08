package spring.training.personal.recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private Long recipeId;
    private BigDecimal amount;
    private String description;
    private UnitOfMeasureCommand uom;
    private RecipeCommand recipe;
}
