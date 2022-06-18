package shelterhelper.service;

import org.springframework.stereotype.Service;
import shelterhelper.repository.AnswerRepository;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    /**
     * @param id_question номер вопроса
     * @return текст ответа
     * на один вопрос может быть несколько записей ответа
     */
    @Override
    public String getAnswer(Long id_question) {
        List<String> answers = answerRepository.getAnswerForQuestion(id_question);
        return answers.toString();
    }
}
