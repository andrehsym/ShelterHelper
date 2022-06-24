package shelterhelper.service;

/**
 * интерфейс работы с БД ответов.
 * Есть номер вопроса - получть текст ответа
 */
public interface AnswerService {
    String getAnswer(Long id_question);
}
