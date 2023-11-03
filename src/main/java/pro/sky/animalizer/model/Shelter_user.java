package pro.sky.animalizer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Класс-модель, описывающая User.
 */
@Entity
public class Shelter_user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String full_name;
    private String phone_number;

    public Shelter_user() {
    }

    public Shelter_user(long id, String full_name, String phone_number) {
        this.id = id;
        this.full_name = full_name;
        this.phone_number = phone_number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter_user that = (Shelter_user) o;
        return id == that.id && Objects.equals(full_name, that.full_name) && Objects.equals(phone_number, that.phone_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, full_name, phone_number);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + full_name + '\'' +
                ", phoneNumber=" + phone_number +
                '}';
    }
}
