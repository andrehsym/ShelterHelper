package shelterhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shelterhelper.model.Answer;
import shelterhelper.model.Question;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAnswersByIdQuestionOrderById(Long idQuestion);
    List<Answer> findAllByOrderByIdQuestionAscIdAsc();

    @Query(value = "SELECT  *  FROM answer WHERE id_question = ?1", nativeQuery = true)
    List<Question> getAnswerForQuestion(Long id);

}
