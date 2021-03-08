package spring.training.personal.recipeapp.services;

import spring.training.personal.recipeapp.commands.UnitOfMeasureCommand;
import spring.training.personal.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import spring.training.personal.recipeapp.domain.UnitOfMeasure;
import spring.training.personal.recipeapp.repositories.UnitOfMeasureRepository;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements
        UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(
            final UnitOfMeasureRepository unitOfMeasureRepository,
            final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert).collect(Collectors.toSet());
    }

    @Override
    public UnitOfMeasure findById(final Long id) {
        return unitOfMeasureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit of Measure not Found"));
    }
}
