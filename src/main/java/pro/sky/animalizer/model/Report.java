package pro.sky.animalizer.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String photoPath;
    String text;

    public Report(String photoPath, String text) {
        this.photoPath = photoPath;
        this.text = text;
    }

    public  Report(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return id == report.id && Objects.equals(photoPath, report.photoPath) && Objects.equals(text, report.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, photoPath, text);
    }

    @Override
    public String toString() {
        return "Report{" +
                "text='" + text + '\'' +
                '}';
    }
}
