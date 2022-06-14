package shelterhelper.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shelterhelper.model.AnswerImage;
import shelterhelper.service.AnswerImageService;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/** Эндпоинты для работы с таблицей картинок (answer_image), в которой
 * находятся картинки для таблицы ответов (answer)
 */

@RestController
public class AnswerImageController {

    private final AnswerImageService answerImageService;

    public AnswerImageController(AnswerImageService answerImageService) {
        this.answerImageService = answerImageService;
    }

/** Загрузка картинки с указанием номера ответа (answerId) в таблице ответов (answer),
 * к которому она относится
 */
    @PostMapping(value = "/{answerId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAnswerImage(@PathVariable Long answerId,
                                               @RequestParam MultipartFile answerImage) throws IOException {
        answerImageService.uploadAnswerImage(answerId, answerImage);
        return ResponseEntity.ok().build();
    }

    /** Получить картинку из таблицы картинок по номеру ответа (answerId),
     * к которому она относится
     */

    @GetMapping(value = "/{answerId}/answerImage")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long answerId) {
        AnswerImage answerImage = answerImageService.findAnswerImage(answerId);
        if (answerImage.getMediaType() == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(answerImage.getMediaType()));
        headers.setContentLength(answerImage.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(answerImage.getData());
    }

    /** Получить список записей в таблице картинок с полной информацией по каждой картинке.
     В запросе указывается максимальное количество выводимых записей (size) и номер записи,
     с которой требуется начать вывод
     */

    @GetMapping("/imagePage")
    public ResponseEntity<List<AnswerImage>> getImagesByPage(@RequestParam int page, @RequestParam int size) {
        List<AnswerImage> pageImages = answerImageService.getPageWithImages(page-1, size);
        if (pageImages.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageImages);
    }

    /** Удалить картинку по идентификатору ответа (answer_id).
     * Одновременно удаляется и соответствующая запись в таблице ответа
     * */

    @DeleteMapping("/delete/{answerId}")
    public ResponseEntity<AnswerImage> deleteImage(@PathVariable Long answerId) {
        answerImageService.deleteImage(answerId);
        return ok().build();
    }

}
