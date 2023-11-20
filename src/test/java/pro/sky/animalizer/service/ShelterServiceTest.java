package pro.sky.animalizer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.sky.animalizer.exceptions.PetNotFoundException;
import pro.sky.animalizer.exceptions.ShelterNotFoundException;
import pro.sky.animalizer.model.*;
import pro.sky.animalizer.repositories.ShelterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShelterServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(ShelterService.class);
    @Mock
    private ShelterRepository repository;
    @InjectMocks
    private ShelterService shelterService;
    Shelter shelterTest = new Shelter("111", "222", "333", "444", "c:\\", "555");
    @Test
    void createShelter() {
        Shelter actualS = shelterService.createShelter(new Shelter("111", "222", "333", "444", "c:\\", "555"));
        assertEquals(repository.save(shelterTest), actualS);
    }
    @Test
    void editShelter() {
        shelterTest.setId(1L);
        Mockito.doReturn(Optional.ofNullable(shelterTest))
                .when(repository).findById(1L);
        assertEquals(Optional.of(shelterTest), repository.findById(1L));
    }
    @Test
    void getShelterById() {
        Shelter shelterActual = new Shelter();
        shelterActual.setId(2L);
        shelterActual.setSecurityPhoneNumber("79999999999");
        shelterActual.setSchedule("QSC");
        shelterActual.setShelterType("Cat");
        Mockito.doReturn(Optional.ofNullable(shelterActual))
                .when(repository).findById(2L);
        assertEquals(Optional.of(shelterActual), repository.findById(2L));
    }
    @Test
    void getAllShelters() {
        List<Shelter> listTest = new ArrayList<>();
        when(repository.findAll()).thenReturn(listTest);
        assertEquals(repository.findAll(), listTest);
    }
    @Test
        void deleteShelter() {
        Shelter s = new Shelter();
        s.setId(1L);
        s.setShelterType("Cat");
        assertThrows(ShelterNotFoundException.class, () -> shelterService.deleteShelter(1L));
    }
}
