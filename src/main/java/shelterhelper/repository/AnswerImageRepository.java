package shelterhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shelterhelper.model.AnswerImage;

import java.util.Optional;

@Repository
public interface AnswerImageRepository extends JpaRepository<AnswerImage, Long> {

    Optional<AnswerImage> findByAnswer_Id(Long answerId);

    boolean existsAnswerImageByAnswer_Id(Long answerId);

        void deleteAnswerImageByAnswer_Id(Long answerId);

}
