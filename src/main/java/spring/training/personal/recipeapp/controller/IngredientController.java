package spring.training.personal.recipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import spring.training.personal.recipeapp.commands.IngredientCommand;
import spring.training.personal.recipeapp.commands.RecipeCommand;
import spring.training.personal.recipeapp.commands.UnitOfMeasureCommand;
import spring.training.personal.recipeapp.services.IngredientService;
import spring.training.personal.recipeapp.services.RecipeService;
import spring.training.personal.recipeapp.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(final RecipeService recipeService,
                                final IngredientService ingredientService,
                                final UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping({"/recipe/{id}/ingredients"})
    public String listIngredients(@PathVariable String id, Model model) {
        if (log.isDebugEnabled()) {
            log.debug("Getting ingredient list for recipe id: " + id);
        }
        // using command object to avoid lazy load errors in Thymeleaf
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/ingredient/list";
    }

    @GetMapping({"/recipe/{recipeId}/ingredients/{id}/show"})
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        return "recipe/ingredient/show";
    }

    @GetMapping({"/recipe/{recipeId}/ingredients/new"})
    public String addNewRecipeIngredient(@PathVariable String recipeId, Model model) {

        recipeService.findCommandById(Long.valueOf(recipeId));

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @GetMapping({"recipe/{recipeId}/ingredients/{id}/update"})
    public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping({"recipe/{recipeId}/ingredient"})
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        if (log.isDebugEnabled()) {
            log.debug("saved receipe id:" + savedCommand.getRecipeId());
            log.debug("saved ingredient id: " + savedCommand.getId());
        }

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredients/" + savedCommand.getId() + "/show";
    }

    @GetMapping({"recipe/{recipeId}/ingredients/{id}/delete"})
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id) {
        if (log.isDebugEnabled() ) {
            log.debug("deleting ingredient id: " + id);
        }
        ingredientService.deleteIngredientById(Long.valueOf(recipeId), Long.valueOf(id));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }
}
