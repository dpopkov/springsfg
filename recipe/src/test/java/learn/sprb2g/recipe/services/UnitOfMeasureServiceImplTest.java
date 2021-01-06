package learn.sprb2g.recipe.services;

import learn.sprb2g.recipe.commands.UnitOfMeasureCommand;
import learn.sprb2g.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import learn.sprb2g.recipe.domain.UnitOfMeasure;
import learn.sprb2g.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Mock
    UnitOfMeasureToUnitOfMeasureCommand converter;
    @InjectMocks
    UnitOfMeasureServiceImpl service;

    @Test
    void testFindAll() {
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(10L);
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(11L);
        when(unitOfMeasureRepository.findAll()).thenReturn(List.of(uom1, uom2));
        when(converter.convert(uom1)).thenReturn(new UnitOfMeasureCommand());
        when(converter.convert(uom2)).thenReturn(new UnitOfMeasureCommand());

        Set<UnitOfMeasureCommand> found = service.findAll();
        assertEquals(2, found.size());
        verify(unitOfMeasureRepository).findAll();
        verify(converter).convert(uom1);
        verify(converter).convert(uom2);
    }
}
