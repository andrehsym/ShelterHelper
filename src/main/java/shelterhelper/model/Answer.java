package shelterhelper.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

/**
 * сущность - ответы. Ответы на все пункты меню бота
 * это все записи из БД question, которые isList = TRUE
 * private Long idQuestion;   идентификатор запроса - уникальный
 * private textAnswer; текст ответа, который будет доступен для редактирования пользователю
 **/
@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;
    @Column(name = "id_question")
    private Long idQuestion;
    @Column(name = "text_answer")
    private String textAnswer;

    public Answer(String id, Long idQuestion, String textAnswer) {
        this.id = id;
        this.idQuestion = idQuestion;
        this.textAnswer = textAnswer;
    }

    public Answer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Long idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(idQuestion, answer.idQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQuestion);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id='" + id + '\'' +
                ", idQuestion=" + idQuestion +
                ", textAnswer='" + textAnswer + '\'' +
                '}';
    }
}
