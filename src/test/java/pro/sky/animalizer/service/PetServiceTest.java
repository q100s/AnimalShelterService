package pro.sky.animalizer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalizer.exceptions.PetNotFoundException;
import pro.sky.animalizer.model.Pet;
import pro.sky.animalizer.repositories.PetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {

    @Mock
    PetRepository petRepositoryMock;
    @InjectMocks
    PetService petService;

    @Test
    void getAllPetTest() {
        List<Pet> listPetTest = new ArrayList<>();
        when(petRepositoryMock.findAll()).thenReturn(listPetTest);
        assertEquals(petService.getAllPets(), listPetTest);

    }

    @Test
    void getPetByIdTest() {
        Pet petTest = new Pet();
        petTest.setId(2L);
        when(petRepositoryMock.findById(2L)).thenReturn(Optional.of(petTest));
        assertEquals(petService.getPetById(2L), petTest);
    }

    @Test
    void getPetByIdTestException() {
        Pet petTest = new Pet();
        petTest.setId(2L);
        assertThrows(PetNotFoundException.class, () -> petService.getPetById(2L));
    }

    @Test
    void createPetTest() {
        Pet actualPet = new Pet();
        when(petRepositoryMock.save(actualPet)).thenReturn(actualPet);
        assertEquals(petService.createPet(actualPet), actualPet);
    }

    @Test
    void editPetTest() {
        Pet petTest = new Pet();
        petTest.setId(1L);
        petTest.setPetType("Cat");
        petTest.setPetName("Vasya");
        petTest.setId(4L);
        Mockito.doReturn(Optional.ofNullable(petTest))
                .when(petRepositoryMock).findById(4L);
        assertEquals(Optional.of(petTest), petRepositoryMock.findById(4L));
    }

    @Test
    void editPetTestException() {
        Pet petTest = new Pet();
        petTest.setId(1L);
        petTest.setPetType("Cat");
        petTest.setPetName("Vasya");
        petTest.setPetName("TestName");
        assertThrows(PetNotFoundException.class, () -> petService.editPet(1L, petTest));
    }

    @Test
    void deletePet() {
        Pet petTest = new Pet();
        petTest.setId(1L);
        petTest.setPetType("Cat");
        petTest.setPetName("Vasya");
        assertThrows(PetNotFoundException.class, () -> petService.deletePet(1L));
    }
}
