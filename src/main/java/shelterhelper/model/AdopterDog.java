package shelterhelper.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 private Long idUser;   идентификатор пользователя
 private Long idDog;    идентификатор  собаки
 private boolean isChecked; -  находится на испытательном сроке?
 private boolean isProblem; Не прошел испытательный срок
 private int amountOfProbationDays; Число основных дней испытательного срока ( по умолчанию 30)
 private int amountOfExtraDays; Число дополнительный дней
**/
@Entity
public class AdopterDog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private Long idDog;
    private boolean isChecked;
    private boolean isProblem;
    private int amountOfProbationDays;
    private int amountOfExtraDays;

    public AdopterDog(Long idUser, Long idDog, boolean isChecked, boolean isProblem, int amountOfProbationDays, int amountOfExtraDays) {
        this.idUser = idUser;
        this.idDog = idDog;
        this.isChecked = isChecked;
        this.isProblem = isProblem;
        this.amountOfProbationDays = amountOfProbationDays;
        this.amountOfExtraDays = amountOfExtraDays;
    }

    public AdopterDog() {
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdDog() {
        return idDog;
    }

    public void setIdDog(Long idDog) {
        this.idDog = idDog;
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
        AdopterDog that = (AdopterDog) o;
        return Objects.equals(idUser, that.idUser) && Objects.equals(idDog, that.idDog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idDog);
    }

    @Override
    public String toString() {
        return "AdopterDog{" +
                "idUser=" + idUser +
                ", idDog=" + idDog +
                ", isChecked=" + isChecked +
                ", isProblem=" + isProblem +
                ", amountOfProbationDays=" + amountOfProbationDays +
                ", amountOfExtraDays=" + amountOfExtraDays +
                '}';
    }
}
