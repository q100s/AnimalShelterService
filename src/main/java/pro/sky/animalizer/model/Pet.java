package pro.sky.animalizer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Класс-модель, описывающая питомца.
 */
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String petType;
    private String petName;
    private String photoUrlPath;

    public Pet() {
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

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPhotoUrlPath() {
        return photoUrlPath;
    }

    public void setPhotoUrlPath(String photoUrlPath) {
        this.photoUrlPath = photoUrlPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id) && Objects.equals(petType, pet.petType)
                && Objects.equals(petName, pet.petName) && Objects.equals(photoUrlPath, pet.photoUrlPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, petType, petName, photoUrlPath);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", petType='" + petType + '\'' +
                ", petName='" + petName + '\'' +
                ", photoUrlPath='" + photoUrlPath + '\'' +
                '}';
    }
}