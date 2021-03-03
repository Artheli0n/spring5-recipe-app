package spring.training.personal.recipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import spring.training.personal.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        if (log.isDebugEnabled()) {
            log.debug("Getting Index Page");
        }

        model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
