package shelterhelper.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * справочник типов питомцев
 * присохранении сразу нормализуем текстовые значения
 */
@Entity
@Table(name = "shelter_entity")
public class ShelterObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entity", unique = true)
    private int idEntity;
    @Column(name = "text_entity")
    private String textEntity;

    public ShelterObject(int idEntity, String textEntity) {
        this.idEntity = idEntity;
        this.textEntity = normalize(textEntity);
    }

    protected final String normalize(String str) {
        if (str == null || str.isEmpty()) {
            return " ";
        }
        return str.toUpperCase();
    }

    public ShelterObject() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShelterObject that = (ShelterObject) o;
        return idEntity == that.idEntity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEntity);
    }

    @Override
    public String toString() {
        return "ShelterObject{" +
                "idEntity=" + idEntity +
                ", textEntity=" + textEntity +
                '}';
    }

    public int getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(int idEntity) {
        this.idEntity = idEntity;
    }

    public String getTextEntity() {
        return textEntity;
    }

    public void setTextEntity(String textEntity) {
        this.textEntity = textEntity;
    }
}