package pro.sky.animalizer.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс-модель, описывающая пользователя.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long telegramId;
    private String telegramNick;
    private String fullName;
    private String phoneNumber;
    private String carNumber;

    public User() {
    }

    /**
     * Конструктор для пользователя с автомобилем
     */
    public User(long telegramId, String telegramNick, String fullName, String phoneNumber, String carNumber) {
        this.telegramId = telegramId;
        this.telegramNick = telegramNick;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.carNumber = carNumber;
    }

    /**
     * Конструктор для пользователя без автомобиля
     */
    public User(long telegramId, String telegramNick, String fullName, String phoneNumber) {
        this.telegramId = telegramId;
        this.telegramNick = telegramNick;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Конструктор для пользователя, созданный по
     * идентификатору и никнейму telegram
     */
    public User(long telegramId, String telegramNick) {
        this.telegramId = telegramId;
        this.telegramNick = telegramNick;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }

    public String getTelegramNick() {
        return telegramNick;
    }

    public void setTelegramNick(String telegramNick) {
        this.telegramNick = telegramNick;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && telegramId == user.telegramId && Objects.equals(telegramNick, user.telegramNick)
                && Objects.equals(fullName, user.fullName) && Objects.equals(phoneNumber, user.phoneNumber)
                && Objects.equals(carNumber, user.carNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, telegramId, telegramNick, fullName, phoneNumber, carNumber);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", telegramId=" + telegramId +
                ", telegramNick='" + telegramNick + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", carNumber='" + carNumber + '\'' +
                '}';
    }
}
