package pro.sky.animalizer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Класс-модель, описывающая приют.
 */
@Entity
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    private String securityPhoneNumber;
    private String schedule;
    private String safetyMeasures;
    private String directionPathFile;
    private String shelterType;

    public Shelter() {
    }

    public Shelter(Long id, String address, String securityPhoneNumber, String schedule, String safetyMeasures, String directionPathFile, String shelterType) {
        this.id = id;
        this.address = address;
        this.securityPhoneNumber = securityPhoneNumber;
        this.schedule = schedule;
        this.safetyMeasures = safetyMeasures;
        this.directionPathFile = directionPathFile;
        this.shelterType = shelterType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSecurityPhoneNumber() {
        return securityPhoneNumber;
    }

    public void setSecurityPhoneNumber(String securityPhoneNumber) {
        this.securityPhoneNumber = securityPhoneNumber;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getSafetyMeasures() {
        return safetyMeasures;
    }

    public void setSafetyMeasures(String safetyMeasures) {
        this.safetyMeasures = safetyMeasures;
    }

    public String getDirectionPathFile() {
        return directionPathFile;
    }

    public void setDirectionPathFile(String directionPathFile) {
        this.directionPathFile = directionPathFile;
    }

    public String getShelterType() {
        return shelterType;
    }

    public void setShelterType(String shelterType) {
        this.shelterType = shelterType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(id, shelter.id) && Objects.equals(address, shelter.address)
                && Objects.equals(securityPhoneNumber, shelter.securityPhoneNumber) && Objects.equals(schedule, shelter.schedule) && Objects.equals(safetyMeasures, shelter.safetyMeasures) && Objects.equals(directionPathFile, shelter.directionPathFile) && Objects.equals(shelterType, shelter.shelterType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, securityPhoneNumber, schedule, safetyMeasures, directionPathFile, shelterType);
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", securityPhoneNumber='" + securityPhoneNumber + '\'' +
                ", schedule='" + schedule + '\'' +
                ", safetyMeasures='" + safetyMeasures + '\'' +
                ", directionPathFile='" + directionPathFile + '\'' +
                ", shelterType='" + shelterType + '\'' +
                '}';
    }
}
