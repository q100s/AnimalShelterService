package pro.sky.animalizer.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * Класс Request для хранения в БД и обработки запросов телеграмм пользователей
 */
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // уникальный id обращения
    private Long chatId; // id телеграмм чата
    private Long telegramId; // id телеграм пользователя
    private String requestText;  // текст обращения - с сутью обращения

    public Request(Long chatId, LocalDateTime requestTime, String requestText) {
        this.chatId = chatId;
        this.requestText = requestText;
    }

    public Request() {
    }

    public Request(Long chatId, Long telegramId, String requestText) {
        this.chatId = chatId;
        this.telegramId = telegramId;
        this.requestText = requestText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }


    public String getRequestText() {
        return requestText;
    }

    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) && Objects.equals(chatId, request.chatId)
                && Objects.equals(telegramId, request.telegramId)
                && Objects.equals(requestText, request.requestText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, telegramId, requestText);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", telegramId=" + telegramId +
                ", requestText='" + requestText + '\'' +
                '}';
    }
}
