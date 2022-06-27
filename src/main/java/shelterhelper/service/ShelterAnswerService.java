package shelterhelper.service;

import shelterhelper.model.Answer;

import java.util.List;

public interface ShelterAnswerService {

    Answer editOrAddAnswer (Answer answer);


    List<Answer> getAnswersAll();

    List<Answer> getAnswersByQuestion(Long id_question);

    Answer getAnswerById(Long id);

    void deleteAnswer(Long id);

}
