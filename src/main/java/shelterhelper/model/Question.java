package shelterhelper.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * сущность - вопросы. Вся текстовая инвормация, которая есть в распоряжении бота
 * название пунктов меню как иерархия, в которой выбор пункта меня зависит от прошлого шага
 * private Long idQuestion;   идентификатор запроса - уникальный
 * private Long idParent;    идентификатор  родителя
 * private isList; -  является листом в иерархии ( нет потомков)?
 * private isNeedAnswer; на данный узел должен быть ответ клиента
 * private textQuestion; текст
 *
 * id_entity  - сущность . Опредеднляем как   enum ShelterEntity
 **/
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_question", unique = true)
    private Long idQuestion;
    @Column(name = "id_parent")
    private Long idParent;
    @Column(name = "id_entity")
    @Enumerated(EnumType.STRING)
    private ShelterEntity shelterEntity;
    @Column(name = "is_list")
    private boolean isList;
    @Column(name = "is_need_answer")
    private boolean isNeedAnswer;
    @Column(name = "text_question")
    private String textQuestion;

    public Question(Long idQuestion, Long idParent, ShelterEntity shelterEntity, boolean isList, boolean isNeedAnswer, String textQuestion) {
        this.idQuestion = idQuestion;
        this.idParent = idParent;
        this.shelterEntity = shelterEntity;
        this.isList = isList;
        this.isNeedAnswer = isNeedAnswer;
        this.textQuestion = textQuestion;
    }

    public Question() {
    }

    public Long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Long idQuestion) {
        this.idQuestion = idQuestion;
    }

    public Long getIdParent() {
        return idParent;
    }

    public void setIdParent(Long idParent) {
        this.idParent = idParent;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

    public boolean isList() {
        return isList;
    }

    public void setList(boolean list) {
        isList = list;
    }

    public boolean isNeedAnswer() {
        return isNeedAnswer;
    }

    public void setNeedAnswer(boolean list) {
        isNeedAnswer = list;
    }

    public ShelterEntity getShelterEntity() {
        return shelterEntity;
    }

    public void setShelterEntity(ShelterEntity shelterEntity) {
        this.shelterEntity = shelterEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(idQuestion, question.idQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQuestion);
    }

    @Override
    public String toString() {
        return "Question{" +
                "idQuestion=" + idQuestion +
                ", idParent=" + idParent +
                ", shelterEntity=" + shelterEntity +
                ", isList=" + isList +
                ", isNeedAnswer=" + isNeedAnswer +
                ", textQuestion='" + textQuestion + '\'' +
                '}';
    }
}
