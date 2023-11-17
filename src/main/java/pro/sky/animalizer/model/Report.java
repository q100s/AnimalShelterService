package pro.sky.animalizer.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate reportDate;
    private String photoPath;
    private String text;
    private Long telegramId;

    public Report() {
    }

    public Report(LocalDate reportDate, String photoPath, String text, Long telegramId) {
        this.reportDate = reportDate;
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

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
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
        return id == report.id && Objects.equals(reportDate, report.reportDate)
                && Objects.equals(photoPath, report.photoPath) && Objects.equals(text, report.text)
                && Objects.equals(telegramId, report.telegramId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportDate, photoPath, text, telegramId);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", dateCreateReport=" + reportDate +
                ", photoPath='" + photoPath + '\'' +
                ", text='" + text + '\'' +
                ", telegramId=" + telegramId +
                '}';
    }
}
