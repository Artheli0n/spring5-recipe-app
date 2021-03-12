package spring.training.personal.recipeapp.controller;

import lombok.extern.slf4j.Slf4j;
import spring.training.personal.recipeapp.commands.RecipeCommand;
import spring.training.personal.recipeapp.exceptions.NotFoundException;
import spring.training.personal.recipeapp.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Slf4j
public class RecipeController {

    public static final String RECIPE_RECIPEFORM = "recipe/recipeform";
    private final RecipeService recipeService;

    public RecipeController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"/recipe/{id}/show"})
    public String GetRecipe(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));

        return "recipe/show";
    }

    @GetMapping({"recipe/new"})
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return RECIPE_RECIPEFORM;
    }

    @GetMapping({"recipe/{id}/update"})
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return RECIPE_RECIPEFORM;
    }

    // @ModelAttribute tells Spring to bind the form post parameters to the RecipeCommand parameter (done through the naming convention)
    // redirect: is a command that tells Thymeleaf to redirect to another html page
//    @RequestMapping(name = "recipe", method = RequestMethod.POST) // old way to do post request mapping
    @PostMapping({"recipe"})
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipe, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(objectError -> log.error(objectError.toString()));
            return RECIPE_RECIPEFORM;
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipe);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping({"recipe/{id}/delete"})
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting id:" + id);

        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception e) {

        log.error("Handling not found exception");
        log.error(e.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", e);

        return modelAndView;
    }
}
