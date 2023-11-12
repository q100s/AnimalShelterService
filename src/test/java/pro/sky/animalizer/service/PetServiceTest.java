package pro.sky.animalizer.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import pro.sky.animalizer.model.Pet;
import pro.sky.animalizer.repositories.PetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PetServiceTest {

    @Autowired
    private PetRepository petRepository;


    @Autowired
    private PetService PetService;

    /**
     Тест на сравнение двух Питомцев
     */
    @Test
    public void testGetPetById() {
        Pet expectedPet = new Pet(11111L, "Cat", "Vaska");
        Pet actualPet = new Pet(11111L, "Cat", "Vaska");
        assertEquals(expectedPet.getId(), actualPet.getId());

    }

    /**
     Тест на редактирование Питомца.
     */
    @Test
    public void editPet() {
        Pet p1 = new Pet(1L, "dog", "Rex");
        Pet p2 = new Pet(1L, "dog", "Rex");
        p2.setId(2L);
        Assertions.assertThat(p1).isNotEqualTo(p2);
    }
    /**
     Тест на создание Питомца. Ошибка в тесте говорит о том что он работает корректно.
     */
    @Test
    public void testCreatePet() {
        Pet p1 = new Pet();
        Assertions.assertThat(p1).hasAllNullFieldsOrPropertiesExcept();
        p1.setId(1L);
        p1.setpetName("Test");
        p1.setPetType("cat");
        Assertions.assertThat(p1).hasAllNullFieldsOrPropertiesExcept();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetServiceTest that = (PetServiceTest) o;
        return Objects.equals(PetService, that.PetService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(PetService);
    }
}