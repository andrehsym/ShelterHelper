package shelterhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shelterhelper.model.Question;

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
}
