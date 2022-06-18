package shelterhelper.service;

import org.springframework.stereotype.Service;
import shelterhelper.model.Answer;

import java.util.List;
import java.util.Objects;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final ShelterAnswerService shelterAnswerService;

    public AnswerServiceImpl(ShelterAnswerService shelterAnswerService) {
        this.shelterAnswerService = shelterAnswerService;
    }

    /**
     * @param id_question номер вопроса
     * @return текст ответа
     * на один вопрос может быть несколько записей ответа
     */
    @Override
    public String getAnswer(Long id_question) {
        List<Answer> answers = shelterAnswerService.getAnswersByQuestion(id_question);
        StringBuilder textAnswer = null;
        for (Answer item : answers) {
            textAnswer.append(item.getTextAnswer());
        }
        return Objects.requireNonNull(textAnswer).toString();
    }
}
