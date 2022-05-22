package shelterhelper.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * БД собак
 *     private Long idDog;    идентификатор
 *     private Long dogName;  имя
 *     private String dogText; описание
 *     private boolean isUsed;  ранее был усыновлен, но вернули назад
 */
@Entity
public class ShelterDogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDog;
    private Long dogName;
    private String dogText;
    private boolean isUsed;

    public ShelterDogs(Long idDog, Long dogName, String dogText, boolean isUsed) {
        this.idDog = idDog;
        this.dogName = dogName;
        this.dogText = dogText;
        this.isUsed = isUsed;
    }

    public ShelterDogs() {
    }

    public Long getIdDog() {
        return idDog;
    }

    public void setIdDog(Long idDog) {
        this.idDog = idDog;
    }

    public Long getDogName() {
        return dogName;
    }

    public void setDogName(Long dogName) {
        this.dogName = dogName;
    }

    public String getDogText() {
        return dogText;
    }

    public void setDogText(String dogText) {
        this.dogText = dogText;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShelterDogs that = (ShelterDogs) o;
        return isUsed == that.isUsed && Objects.equals(idDog, that.idDog) && Objects.equals(dogName, that.dogName) && Objects.equals(dogText, that.dogText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDog);
    }

    @Override
    public String toString() {
        return "ShelterDogs{" +
                "idDog=" + idDog +
                ", dogName=" + dogName +
                ", dogText='" + dogText + '\'' +
                ", isUsed=" + isUsed +
                '}';
    }
}
