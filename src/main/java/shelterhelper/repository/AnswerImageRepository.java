package shelterhelper.repository;

import liquibase.pro.packaged.id;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shelterhelper.model.AnswerImage;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerImageRepository extends JpaRepository<AnswerImage, Long> {

    Optional<AnswerImage> findByAnswer_Id(Long answerId);

    boolean existsAnswerImageByAnswer_Id(Long answerId);

        void deleteAnswerImageByAnswer_Id(Long answerId);

    //    List<AnswerImage> findByIdQuestion(Long idQuestion);

}
