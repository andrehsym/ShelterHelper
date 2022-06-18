package shelterhelper.repository;

import org.hibernate.mapping.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shelterhelper.model.Question;

import java.util.List;

/**
 * Репозиторий. Просто репозиторий
 * написать в репозитории метод
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
    /**
     * так как БД вопросов маленькая  - будем получать записи быстро, поэтому используем  запрос к БД
     * @param id идентификатор
     * @return экземпляр класса
     */
    @Query(value = "SELECT  *  FROM question WHERE id_question = ?1", nativeQuery = true)
    Question getQuestionById(Long id);

    @Query(value = "SELECT  *  FROM question WHERE id_parent = ?1", nativeQuery = true)
    List<Question> getQuestionByParent(Long id);

    @Query(value = "SELECT  id_parent  FROM question WHERE id_question = ?1", nativeQuery = true)
    Long getParentById(Long id);
}
