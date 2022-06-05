package shelterhelper.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * БД собак
 * private Long idPet;    идентификатор
 * idEntity - cущность ( кот, собака, и запись - по умолчанию, нужна для настроек меню
 * private Long petName;  имя
 * private String petText; описание
 * private boolean isUsed;  уже усыновлен
 */
@Entity
@Table(name = "shelter_pets")

public class ShelterPets {

    @Id
    @GeneratedValue
    @Column(name = "id_pet", unique = true)
    private Long idPet;
    @ManyToOne
    @JoinColumn(name = "id_entity")
    private ShelterObject idEntity;
    @Column(name = "pet_name")
    private String petName;
    @Column(name = "pet_text")
    private String petText;
    @Column(name = "is_used")
    private boolean isUsed;

    public ShelterPets(Long idPet, ShelterObject idEntity, String petName, String petText, boolean isUsed) {
        this.idPet = idPet;
        this.idEntity = idEntity;
        this.petName = petName;
        this.petText = petText;
        this.isUsed = isUsed;
    }

    public ShelterPets() {
    }

    public Long getIdPet() {
        return idPet;
    }

    public void setIdPet(Long idDog) {
        this.idPet = idDog;
    }

    public ShelterObject getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(ShelterObject idEntity) {
        this.idEntity = idEntity;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String dogName) {
        this.petName = dogName;
    }

    public String getPetText() {
        return petText;
    }

    public void setPetText(String dogText) {
        this.petText = dogText;
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
        ShelterPets that = (ShelterPets) o;
        return idPet.equals(that.idPet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPet);
    }

    @Override
    public String toString() {
        return "ShelterPets{" +
                "idPet=" + idPet +
                ", shelterEntity=" + idEntity +
                ", petName='" + petName + '\'' +
                ", petText='" + petText + '\'' +
                ", isUsed=" + isUsed +
                '}';
    }
}
