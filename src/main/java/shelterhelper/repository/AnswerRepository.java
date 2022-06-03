package shelterhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shelterhelper.model.Answer;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findAnswersByIdQuestionOrderById(Long idQuestion);
    List<Answer> findAllByOrderByIdQuestionAscIdAsc();

}
