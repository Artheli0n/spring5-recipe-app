package spring.training.personal.recipeapp.services;

import spring.training.personal.recipeapp.commands.UnitOfMeasureCommand;
import spring.training.personal.recipeapp.domain.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUoms();

    UnitOfMeasure findById(Long id);
}
