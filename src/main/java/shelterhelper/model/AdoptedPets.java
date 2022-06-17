package shelterhelper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

/**
 * сущность таблицы усыновителей

 * idUser - идентификатор пользователя
 * idPet   -идентификатор животного ( одна таблица ShelterPets!)
 * isChecked  - на испытательном сроке
 * isAdopter  - стал полноправным усыновителем
 * dateProbation - дата начала испытатлельного срока
 * amountOfProbationDays - число дней испытательного срока
 * amountOfExtraDays     - число дополнительных дней
 *
 * таблицу заполняет сотрудник приюта, когда принимкет решение о начале испытательного срока
 * is_checked = true - весь испытательный срок
 * по окончанию испытательного срока is_checked = false
 * is_adopter =  true  претендет окончательно признан новым хозяином питомца
 * idEntity - cущность ( кот, собака, и запись - по умолчанию, нужна для настроек меню
 */
@Entity
@Table(name = "adopter_pets")
//@Inheritance(
//        strategy = InheritanceType.TABLE_PER_CLASS
//)
public   class AdoptedPets{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    public Long id;
    @Column(name = "id_user")
    public Long idUser;
    @Column(name = "id_pet")
    public Long idPet;
    @Column(name = "is_checked")
    public boolean isChecked;
    @Column(name = "is_adopter")
    public boolean isAdopter;
    @Column(name = "date_Probation")
    public LocalDate dateProbation;
    @Column(name = "amount_of_probation_days")
    public int amountOfProbationDays;
    @Column(name = "amount_of_extra_days")
    public int amountOfExtraDays;
    @ManyToOne
    @JoinColumn(name = "id_entity")
    private ShelterObject idEntity;
    @OneToMany(mappedBy = "adoptedPets", cascade=CascadeType.ALL)
    @JsonIgnore
    private Collection<Reports> reports;

    public AdoptedPets(Long id, Long idUser, Long idPet, boolean isChecked, boolean isAdopter, LocalDate dateProbation, int amountOfProbationDays, int amountOfExtraDays, ShelterObject idEntity) {
        this.id = id;
        this.idUser = idUser;
        this.idPet = idPet;
        this.isChecked = isChecked;
        this.isAdopter = isAdopter;
        this.dateProbation = dateProbation;
        this.amountOfProbationDays = amountOfProbationDays;
        this.amountOfExtraDays = amountOfExtraDays;
        this.idEntity = idEntity;
    }

    public AdoptedPets() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShelterObject getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(ShelterObject idEntity) {
        this.idEntity = idEntity;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }


    public Long getIdPet() {
        return idPet;
    }

    public void setIdPet(Long idDog) {
        this.idPet = idDog;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isAdopter() {
        return isAdopter;
    }

    public void setAdopter(boolean adopter) {
        isAdopter = adopter;
    }

    public LocalDate getDateProbation() {
        return dateProbation;
    }

    public void setDateProbation(LocalDate dateProbation) {
        this.dateProbation = dateProbation;
    }

    public int getAmountOfProbationDays() {
        return amountOfProbationDays;
    }

    public void setAmountOfProbationDays(int amountOfProbationDays) {
        this.amountOfProbationDays = amountOfProbationDays;
    }

    public int getAmountOfExtraDays() {
        return amountOfExtraDays;
    }

    public void setAmountOfExtraDays(int amountOfExtraDays) {
        this.amountOfExtraDays = amountOfExtraDays;
    }

    public Collection<Reports> getReports() {
        return reports;
    }

    public void setReports(Collection<Reports> reports) {
        this.reports = reports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdoptedPets that = (AdoptedPets) o;
        return idUser.equals(that.idUser) && idPet.equals(that.idPet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idPet);
    }

    @Override
    public String toString() {
        return "AdoptedPets{" +
                "idUser=" + idUser +
                ", idPet=" + idPet +
                ", idEntity=" + idEntity +
                ", isChecked=" + isChecked +
                ", isAdopter=" + isAdopter +
                ", dateProbation=" + dateProbation +
                ", amountOfProbationDays=" + amountOfProbationDays +
                ", amountOfExtraDays=" + amountOfExtraDays +
                '}';
    }
}
