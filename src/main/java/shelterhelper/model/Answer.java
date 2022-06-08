package shelterhelper.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "id_question")
    private Long idQuestion;
    @Column(name = "text_answer")
    private String textAnswer;

    @OneToOne(mappedBy = "answer")
    private AnswerImage answerImage;

    public Answer(Long id, Long idQuestion, String textAnswer) {
        this.id = id;
        this.idQuestion = idQuestion;
        this.textAnswer = textAnswer;
    }

    public Answer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(long idQuestion) {
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
