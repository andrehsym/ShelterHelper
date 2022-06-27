package shelterhelper.service;

import org.springframework.stereotype.Service;
import shelterhelper.excepton.IdNotFoundException;
import shelterhelper.model.Answer;
import shelterhelper.model.AnswerImage;
import shelterhelper.repository.AnswerImageRepository;
import shelterhelper.repository.AnswerRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ShelterAnswerServiceImpl implements ShelterAnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerImageRepository answerImageRepository;

    public ShelterAnswerServiceImpl(AnswerRepository answerRepository, AnswerImageRepository answerImageRepository) {
        this.answerRepository = answerRepository;
        this.answerImageRepository = answerImageRepository;
    }

    @Override
    public Answer editOrAddAnswer(Answer answer) {
        Long idQuestion = answer.getIdQuestion();
        List<Answer> listAnswersByQuestion = answerRepository.findAnswersByIdQuestionOrderById(idQuestion);
        if (listAnswersByQuestion.size() == 0) {
            return null;
        }
        return answerRepository.save(answer);
    }

    @Override
//    @Transactional
    public List<Answer> getAnswersAll() {
        return answerRepository.findAllByOrderByIdQuestionAscIdAsc();
    }

    @Override
    public List<Answer> getAnswersByQuestion(Long id_question) {
        return answerRepository.findAnswersByIdQuestionOrderById(id_question);
    }

    @Override
    public Answer getAnswerById(Long id) {
        return answerRepository.findById(id).
                orElseThrow(() -> new IdNotFoundException("Информация по идентификатору не найдена" + id));
    }

    @Override
    public void deleteAnswer(Long id) {
//        answerRepository.findById(id).
//                orElseThrow(() -> new IdNotFoundException("Информация по идентификатору не найдена" + id));
//        AnswerImage answerImage = answerImageRepository.findByAnswer_Id(id).orElse(new AnswerImage());
        if (answerImageRepository.existsAnswerImageByAnswer_Id(id)) {
            answerImageRepository.deleteAnswerImageByAnswer_Id(id);
            return;
        }
        answerRepository.deleteById(id);
    }
}
