package pro.sky.animalizer.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long telegramId;
    private LocalDate dateCreateReport;

    private String photoPath;

    private String text;

    private Long telegramId;

    public Report() {
    }

    public Report(String photoPath, String text, Long telegramId) {
        this.photoPath = photoPath;
        this.text = text;
        this.telegramId = telegramId;
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

    public LocalDate getDateCreateReport() {
        return dateCreateReport;
    }

    public void setDateCreateReport(LocalDate dateCreateReport) {
        this.dateCreateReport = dateCreateReport;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id == report.id && Objects.equals(photoPath, report.photoPath) && Objects.equals(text, report.text)
                && Objects.equals(telegramId, report.telegramId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, photoPath, text, telegramId);
    }
}
