package shelterhelper.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * сущность таблицы усыновителей
 * создана для кошек - собаки далее наследуют
 * idUser - идентификатор пользователя
 * idPet   -идентификатор животного ( одна таблица ShelterPets!)
 * isChecked  - наиспытательном сроке
 * isProblem  - не прошел проверку
 * amountOfProbationDays - число дней испытательного срока
 * amountOfExtraDays     - число дополнительных дней
 */
@Entity
@Inheritance(
        strategy = InheritanceType.TABLE_PER_CLASS
)
@Table(name = "adopter_cats")
public class AdopterCats {
    @Id
    @GeneratedValue
    @Column(name = "id_user", unique = true)
    public Long idUser;
    @Column(name = "id_pet", unique = true)
    public Long idPet;
    @Column(name = "id_checked")
    public boolean isChecked;
    @Column(name = "id_problem")
    public boolean isProblem;
    @Column(name = "amount_of_probation_days")
    public int amountOfProbationDays;
    @Column(name = "amount_of_extra_days")
    public int amountOfExtraDays;

    public AdopterCats(Long idUser, Long idPet, boolean isChecked, boolean isProblem, int amountOfProbationDays, int amountOfExtraDays) {
        this.idUser = idUser;
        this.idPet = idPet;
        this.isChecked = isChecked;
        this.isProblem = isProblem;
        this.amountOfProbationDays = amountOfProbationDays;
        this.amountOfExtraDays = amountOfExtraDays;
    }

    public AdopterCats() {
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

    public boolean isProblem() {
        return isProblem;
    }

    public void setProblem(boolean problem) {
        isProblem = problem;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdopterDogs that = (AdopterDogs) o;
        return Objects.equals(idUser, that.idUser) && Objects.equals(idPet, that.idPet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idPet);
    }

    @Override
    public String toString() {
        return "AdopterCats{" +
                "idUser=" + idUser +
                ", idDog=" + idPet +
                ", isChecked=" + isChecked +
                ", isProblem=" + isProblem +
                ", amountOfProbationDays=" + amountOfProbationDays +
                ", amountOfExtraDays=" + amountOfExtraDays +
                '}';
    }
}
