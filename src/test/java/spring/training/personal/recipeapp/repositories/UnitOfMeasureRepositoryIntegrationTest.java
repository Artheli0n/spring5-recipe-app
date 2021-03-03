package spring.training.personal.recipeapp.repositories;

import spring.training.personal.recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIntegrationTest {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    // thanks to @DataJpaTest, we have access to data in database
    @Test
//    @DirtiesContext // if adding this annotation, next test is gonna take as long as this one because it will force reload the spring context
    void findByDescription() {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", uomOptional.get().getDescription());
    }

    // because Spring context is still available thanks to previous step, this one takes 12ms instead of 504 for the previous one
    @Test
    void findByDescriptionCup() {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Cup");

        assertEquals("Cup", uomOptional.get().getDescription());
    }
}
