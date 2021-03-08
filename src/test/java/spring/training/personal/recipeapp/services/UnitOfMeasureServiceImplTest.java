package spring.training.personal.recipeapp.services;

import spring.training.personal.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import spring.training.personal.recipeapp.domain.UnitOfMeasure;
import spring.training.personal.recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,
                new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void listAllUoms() {
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();

        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);

        unitOfMeasures.add(uom1);
        unitOfMeasures.add(uom2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        assertEquals(unitOfMeasures.size(), unitOfMeasureService.listAllUoms().size());
        verify(unitOfMeasureRepository).findAll();
    }

    @Test
    void findById() {
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);

        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(uom1));

        assertEquals(uom1.getId(), unitOfMeasureService.findById(1L).getId());
        verify(unitOfMeasureRepository).findById(anyLong());
    }
}
