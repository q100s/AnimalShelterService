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
    private Long id;
    private Long telegramId;
    private String telegramNick;
    private String fullName;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User() {
    }

    public User(Long telegramId, String telegramNick) {
        this.telegramId = telegramId;
        this.telegramNick = telegramNick;
    }

    public User(Long telegramId, String telegramNick, String fullName, String phoneNumber, UserType userType) {
        this.telegramId = telegramId;
        this.telegramNick = telegramNick;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType setUserType) {
        this.userType = setUserType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(telegramId, user.telegramId)
                && Objects.equals(telegramNick, user.telegramNick)
                && Objects.equals(fullName, user.fullName);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, telegramId, telegramNick, fullName, phoneNumber);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", telegramId=" + telegramId +
                ", telegramNick='" + telegramNick + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
