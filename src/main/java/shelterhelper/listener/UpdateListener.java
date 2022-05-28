package shelterhelper.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import shelterhelper.repository.QuestionRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UpdateListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(UpdateListener.class);
    private TelegramBot telegramBot;
    private final QuestionRepository questionRepository;

    public UpdateListener(TelegramBot telegramBot, QuestionRepository questionRepository) {
        this.telegramBot        = telegramBot;
        this.questionRepository = questionRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(updates -> {
            process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (update.message().text().equals("/start")) {
                startMethod(update);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * текстовые значения получаем из БД вопросов question
     * @param update параметр
     */
    private void startMethod(Update update) {
        SendMessage messageText = new SendMessage(update.message().chat().id(), questionRepository.getQuestionById(1L).getTextQuestion()).parseMode(ParseMode.HTML);

        InlineKeyboardButton firstButton  = new InlineKeyboardButton(questionRepository.getQuestionById(2L).getTextQuestion());
        InlineKeyboardButton secondButton = new InlineKeyboardButton(questionRepository.getQuestionById(3L).getTextQuestion());
        InlineKeyboardButton thirdButton  = new InlineKeyboardButton(questionRepository.getQuestionById(4L).getTextQuestion());
        InlineKeyboardButton fourthButton = new InlineKeyboardButton(questionRepository.getQuestionById(5L).getTextQuestion());
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                new InlineKeyboardButton[][]{{firstButton.callbackData("firstButton")},
                        {secondButton.callbackData("secondButton")},
                        {thirdButton.callbackData("thirdButton")},
                        {fourthButton.callbackData("fourthButton")}});
        messageText.replyMarkup(inlineKeyboard);
        telegramBot.execute(messageText);
    }

}
