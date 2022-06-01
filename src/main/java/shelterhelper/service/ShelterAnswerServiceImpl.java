package shelterhelper.service;

import org.springframework.stereotype.Service;
import shelterhelper.model.Answer;
import shelterhelper.repository.AnswerRepository;

import java.util.List;

@Service
public class ShelterAnswerServiceImpl implements ShelterAnswerService {

    private final AnswerRepository answerRepository;

    public ShelterAnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Answer editOrAddAnswer(Answer answer) {
        Long idQuestion = answer.getIdQuestion();
        List<Answer> listAnswersByQuestion = answerRepository.findAnswersByIdQuestion(idQuestion);
        if (listAnswersByQuestion.size() == 0) {
            return null;
        }
        return answerRepository.save(answer);
    }

    @Override
    public List<Answer> getAnswersAll() {
        return answerRepository.findAll();
    }

    @Override
    public List<Answer> getAnswersByQuestion(Long id_question) {
        return answerRepository.findAnswersByIdQuestion(id_question);
    }

    @Override
    public Answer getAnswerById(Long id) {
        return answerRepository.getById(id);
    }

    @Override
    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }
}
