package pro.sky.animalizer.model;

import javax.persistence.Entity;
import java.util.Objects;

/**
 * Класс-модель, описывающая питомца.
 */
@Entity
public class Pet {
    private Long id;
    private String petType;
    private String petName;




public class User {
    /**
     * заглушка
     */
}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getpetName() {
        return petName;
    }

    public void setpetName(String petName) {
        this.petName = petName;
    }


    public Pet() {
    }


    public Pet(Long id, String petType, String petName) {
        this.id = id;
        this.petType = petType;
        this.petName = petName;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id) && Objects.equals(petType, pet.petType) && Objects.equals(petName, pet.petName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, petType, petName);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", petType='" + petType + '\'' +
                ", petName='" + petName + '\'' +
                '}';
    }
}