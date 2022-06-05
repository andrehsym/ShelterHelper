package shelterhelper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shelterhelper.model.ShelterObject;

import java.util.List;

@Repository
public interface ShelterObjectRepository extends JpaRepository<ShelterObject, Long> {
    /**
     * справочник питомцев. На данный момент только кошки и собаки
     * используется как флаг в табдице всех питомцев и меню в боте
     * так как БД вопросов маленькая  - будем получать записи быстро, поэтому используем  запрос к БД
     *
     * @param str по которому  найдемидентификатор
     *            все названия нормализованы( с большой буквы и без пробелов)
     * @return идентификатор
     */
    @Query(value = "SELECT  id_entity  FROM shelter_entity WHERE text_entity = ?1", nativeQuery = true)
    int getIdByText(String str);


}
