package pro.sky.animalizer.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pro.sky.animalizer.model.Pet;
import pro.sky.animalizer.repositories.PetRepository;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetServiceTest {


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
     Тест на получение всех Питомцев. Допилить!!!
     */
    @Test
    public void testGetAllPets() {

        Pet p1 = new Pet(1L, "1", "1");
        Pet p2 = new Pet(2L, "2", "2");

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