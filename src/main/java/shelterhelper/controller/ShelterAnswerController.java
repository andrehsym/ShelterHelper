package shelterhelper.controller;

import org.springframework.web.bind.annotation.*;
import shelterhelper.model.Answer;
import shelterhelper.service.ShelterAnswerService;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/answer")
public class ShelterAnswerController {

    private final ShelterAnswerService shelterAnswerService;

    public ShelterAnswerController(ShelterAnswerService shelterAnswerService) {
        this.shelterAnswerService = shelterAnswerService;
    }

    /** Редактировать или дописать ответ PUT http://localhost:8080/answer
     * Ответ на вопрос может состоять из нескольких частей, каждая из которых
     * не более 255 знаков и имеет свой id. Принадлежность ответа к вопросу определяется в
     * столбце id_question, где находится номер вопроса, к которому относится ответ.
     * Изначально в таблице присутствуют ответы на все вопросы. При добавлении или
     * редактировании ответа, значение id_question должно быть равно одному из
     * уже существующих в таблице. Если это не выполняется возвращается ошибка.
     * При добавлении новой части ответа id не заполняется, он генерится автоматически.
     **/

    @PutMapping
    public ResponseEntity<Answer> updateAnswer(@RequestBody Answer answer) {
        Answer editAnswer = shelterAnswerService.editOrAddAnswer(answer);
        if (editAnswer == null) {
            return ResponseEntity.notFound().build();
        }
        return ok(editAnswer);
    }

    /**
     * Получить все существующие ответы GET http://localhost:8080/answer/all
     **/
    @GetMapping("/all")
    public ResponseEntity<List<Answer>> getAnswersAll() {
        List<Answer> listOfAnswers = shelterAnswerService.getAnswersAll();
        if (listOfAnswers.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listOfAnswers);
    }

    /**
     * Получить все ответы по определенному вопросу, то-есть по id_question
     * GET http://localhost:8080/answer/byQuestion/{id_question}
     **/
    @GetMapping("/byQuestion/{id_question}")
    public ResponseEntity<List<Answer>> getAnswersByQuestion(@PathVariable Long id_question) {
        List<Answer> listOfAnswers = shelterAnswerService.getAnswersByQuestion(id_question);
        if (listOfAnswers.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listOfAnswers);
    }

    /**
     * Получить ответ или определенную часть ответа (если ответ имеет
     * несколько частей) по его идентификатору, то-есть по id
     * GET http://localhost:8080/answer/byId/{id}
     **/
    @GetMapping("/byId/{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable long id) {
        Answer answer = shelterAnswerService.getAnswerById(id);
        return ok(answer);
    }

    /**
     * Удалить ответ или определенную часть ответа (если ответ имеет
     * несколько частей) по его идентификатору, то-есть по id
     * DELETE http://localhost:8080/answer/delete/{id}
     **/
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Answer> deleteAnswer(@PathVariable Long id) {
        shelterAnswerService.deleteAnswer(id);
        return ok().build();
    }
}
