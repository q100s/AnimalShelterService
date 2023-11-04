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
    private String petBreed;
    private User petOwner;



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

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public User getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(User petOwner) {
        this.petOwner = petOwner;
    }

    public Pet(Long id, String petType, String petBreed, User petOwner) {
        this.id = id;
        this.petType = petType;
        this.petBreed = petBreed;
        this.petOwner = petOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id) && Objects.equals(petType, pet.petType) && Objects.equals(petBreed, pet.petBreed) && Objects.equals(petOwner, pet.petOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, petType, petBreed, petOwner);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", petType='" + petType + '\'' +
                ", petBreed='" + petBreed + '\'' +
                ", petOwner='" + petOwner + '\'' +
                '}';
    }
}
