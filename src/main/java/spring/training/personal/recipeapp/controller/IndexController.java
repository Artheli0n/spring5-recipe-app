package spring.training.personal.recipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import spring.training.personal.recipeapp.domain.Category;
import spring.training.personal.recipeapp.domain.UnitOfMeasure;
import spring.training.personal.recipeapp.repositories.CategoryRepository;
import spring.training.personal.recipeapp.repositories.UnitOfMeasureRepository;
import spring.training.personal.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeService recipeService;

    public IndexController(final CategoryRepository categoryRepository,
                           final UnitOfMeasureRepository unitOfMeasureRepository,
                           final RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        if (log.isDebugEnabled()) {
            log.debug("Getting Index Page");
        }

        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        if (log.isDebugEnabled()) {
            log.debug("Cat Id is:" + categoryOptional.get().getId());
            log.debug("UOM Id is:" + unitOfMeasureOptional.get().getId());
        }

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
