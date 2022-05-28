package shelterhelper.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * БД собак
 * private Long idDog;    идентификатор
 * private Long dogName;  имя
 * private String dogText; описание
 * private boolean isUsed;  уже усыновлен
 */
@Entity
@Table(name = "shelter_dogs")
public class ShelterDogs {

    @Id
    @GeneratedValue
    @Column(name = "id_dog", unique = true)
    private Long idDog;
    @Column(name = "dog_name")
    private String dogName;
    @Column(name = "dog_text")
    private String dogText;
    @Column(name = "is_used")
    private boolean isUsed;

    public ShelterDogs(Long idDog, String dogName, String dogText, boolean isUsed) {
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

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
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
        return idDog.equals(that.idDog);
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
