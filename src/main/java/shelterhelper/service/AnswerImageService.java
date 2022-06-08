package shelterhelper.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shelterhelper.excepton.IdNotFoundException;
import shelterhelper.model.Answer;
import shelterhelper.model.AnswerImage;
import shelterhelper.repository.AnswerImageRepository;
import shelterhelper.repository.AnswerRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.util.List;

@Service
@Transactional
public class AnswerImageService {

    private final Logger logger = LoggerFactory.getLogger(AnswerImageService.class);
    private AnswerImageRepository answerImageRepository;
    private AnswerRepository answerRepository;
    private ShelterAnswerService shelterAnswerService;

    public AnswerImageService(AnswerImageRepository answerImageRepository, AnswerRepository answerRepository, ShelterAnswerService shelterAnswerService) {
        this.answerImageRepository = answerImageRepository;
        this.answerRepository = answerRepository;
        this.shelterAnswerService = shelterAnswerService;
    }

    public void uploadAnswerImage(Long answerId, MultipartFile answerImageFile) throws IOException {
        logger.info("Method was called - uploadAnswerImage");
        Answer answer = answerRepository.findById(answerId).
                orElseThrow(() -> new IdNotFoundException("Такого идентификатора в таблице answer не существует" + answerId));
        AnswerImage answerImage = findAnswerImage(answerId);
        answerImage.setAnswer(answer);
        answerImage.setFileSize(answerImageFile.getSize());
        answerImage.setMediaType(answerImageFile.getContentType());
        answerImage.setData(answerImageFile.getBytes());
        answerImageRepository.save(answerImage);
    }

    public AnswerImage findAnswerImage(Long answerId) {
        AnswerImage answerImage = answerImageRepository.findByAnswer_Id(answerId).orElse(new AnswerImage());
        return answerImage;
    }

    public List<AnswerImage> getPageWithImages(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return answerImageRepository.findAll(pageRequest).getContent();
    }

    public void deleteImage(Long id) {
        AnswerImage answerImage = answerImageRepository.findByAnswer_Id(id).
                orElseThrow(() -> new IdNotFoundException("Такого идентификатора в таблице answer не существует" + id));
        answerImageRepository.deleteById(answerImage.getId());
//                orElseThrow(() -> new IdNotFoundException("Такого идентификатора в таблице answer не существует" + id));

//        answerImageRepository.deleteById(id);
//        answerImageRepository.deleteAllById();
    }

}
