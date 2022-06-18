package shelterhelper.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import shelterhelper.repository.QuestionRepository;

import javax.annotation.PostConstruct;
import java.util.List;

import static shelterhelper.model.Constants.*;

@Service
public class UpdateListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(UpdateListener.class);
    private TelegramBot telegramBot;
    private final QuestionRepository questionRepository;

    private String cat_emoji = EmojiParser.parseToUnicode(EMOJI_CAT);
    private String dog_emoji = EmojiParser.parseToUnicode(EMOJI_DOG);
    private String question_emoji = EmojiParser.parseToUnicode(EMOJI_QUESTION);
    private String man_emoji = EmojiParser.parseToUnicode(EMOJI_PERSON);
    private String pencil_emoji = EmojiParser.parseToUnicode(EMOJI_PENCIL);

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
                startMethod(update);
                checkingCallbackQuery(update);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * текстовые значения получаем из БД вопросов question
     *
     * @param update параметр
     */

    private void startMethod(Update update) {
        try {
            if (update.message().text().equals("/start")) {
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        /*Приветствие из БД + */getQuestion(1L) + "\nВыберите приют: ")
                        .parseMode(ParseMode.HTML)
                        .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                                {{new InlineKeyboardButton(getQuestion(2L) + cat_emoji).callbackData("catShelter")},
                                 {new InlineKeyboardButton(getQuestion(3L)  + dog_emoji).callbackData("dogShelter")}})));
            }
        } catch (NullPointerException e) {
            logger.info("Exception: {}", e + " in startMethod");
        }
    }

    private void checkingCallbackQuery(Update update) {
        try {
            if (update.callbackQuery().data().startsWith("c")) {
                checkingCallbackQueryCat(update);
            } else if (update.callbackQuery().data().startsWith("d")) {
                checkingCallbackQueryDog(update);
            }
        } catch (NullPointerException e) {
            logger.info("Exception: {}", e + " in checkingCallbackQuery method");
        }
    }

    private void checkingCallbackQueryDog(Update update) {
        switch (update.callbackQuery().data()) {
            case "dogShelter":
                dogShelter(update);
                break;
            case "dogShelterInfo":
                dogShelterInfo(update);
                break;
            case "dogAdopt":
                dogAdopt(update);
                break;
            case "dogReport":
                dogReport(update);
                break;
        }
    }

    private void checkingCallbackQueryCat(Update update) {
        switch (update.callbackQuery().data()) {
            case "catShelter":
                catShelter(update);
                break;
            case "catShelterInfo":
                catShelterInfo(update);
                break;
            case "catAdopt":
                catAdopt(update);
                break;
            case "catReport":
                catReport(update);
                break;
        }
    }

    //пользовательские кнопки
    private void replyKeyboard(Update update) {
        SendMessage messsage = new SendMessage(update.callbackQuery().from().id(), null);
        Keyboard keyboard = new ReplyKeyboardMarkup(
                new KeyboardButton[][]
                        {{new KeyboardButton("Позвать волонтера"),
                                new KeyboardButton("Записать контакты")},
                                {new KeyboardButton("Вернуться в главное меню")}}
        );

//        Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
//                new String[]{"Позвать волонтера", "Записать контакты"},
//                new String[]{"Вернуться в главное меню"})
//                .oneTimeKeyboard(true)   // optional
//                .resizeKeyboard(true);
        telegramBot.execute(messsage.replyMarkup(keyboard));
    }

    private void catShelter(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), getQuestion(2L) )
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton(cat_emoji + getQuestion(4L) + question_emoji ).callbackData("catShelterInfo")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(5L) + question_emoji ).callbackData("catAdopt")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(6L) + pencil_emoji ).callbackData("catReport")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(7L) + man_emoji  ).callbackData("callVolunteer")}})));
    }

    private void catShelterInfo(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), getQuestion(4L))
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton(cat_emoji + getQuestion(8L)  + question_emoji).callbackData("catShelterAbout")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(9L)  + question_emoji).callbackData("catSheduleScheme")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(10L) + question_emoji).callbackData("catPass")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(12L) + pencil_emoji).callbackData("callClientContacts")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(7L)  + man_emoji).callbackData("callVolunteer")}})));
    }

    private void catAdopt(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), /*Приветствие из БД + */"Как взять кошку из приюта: ")
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton(cat_emoji + getQuestion(14L)  + question_emoji).callbackData("catRules")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(15L)  + question_emoji).callbackData("catDocs")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(16L)  + question_emoji ).callbackData("catRecsTransportation")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(17L)  + question_emoji).callbackData("catRecsHomeKitty")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(18L)  + question_emoji).callbackData("catRecsHomeAdult")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(19L)  + question_emoji).callbackData("catRecsDisabled")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(22L)  + question_emoji).callbackData("catRejectionReasons")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(23L)  + pencil_emoji).callbackData("catClientContacts")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(7L) + man_emoji ).callbackData("callVolunteer")}})));
    }

    private void catReport(Update update) {
        SendMessage messageText = new SendMessage(update.message().chat().id(),  "Ведение питомца:").parseMode(ParseMode.HTML);
        InlineKeyboardButton catFormReportInfo  = new InlineKeyboardButton("Форма ежедневного отчета ");
        InlineKeyboardButton catSendPhoto = new InlineKeyboardButton("Прислать фото кошки");
        //Дальше обработка действий клиента при отправке отчета о кошке. Если отослал фото - проверка и
        // просьба отослать информацию о здоровье, питании и изменениях поведения
        // + напоминалка

        //кнопки -->
//        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
//                new InlineKeyboardButton[][]{{???.callbackData("???")},
//                        {???.callbackData("???")}});
//        messageText.replyMarkup(inlineKeyboard);
//        telegramBot.execute(messageText);
    }

    private void dogShelter(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), /*Приветствие из БД + */ getQuestion(3L) )
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton(dog_emoji + getQuestion(4L) + question_emoji).callbackData("dogShelterInfo")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(5L) + question_emoji).callbackData("dogAdopt")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(6L) + pencil_emoji).callbackData("dogReport")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(7L) + man_emoji).callbackData("callVolunteer")}})));
    }

    private void dogShelterInfo(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), dog_emoji + getQuestion(4L))
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton(dog_emoji + getQuestion(8L)  + question_emoji).callbackData("dogShelterAbout")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(9L)  + question_emoji).callbackData("dogSheduleScheme")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(10L) + question_emoji).callbackData("dogPass")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(11L) + question_emoji).callbackData("dogPrevention")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(12L) + pencil_emoji).callbackData("dogClientContacts")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(7L)  + man_emoji).callbackData("callVolunteer")}})));
    }

    private void dogAdopt(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), /*Приветствие из БД + */"Как взять собаку из приюта: ")
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton(dog_emoji + getQuestion(14L)  + question_emoji).callbackData("dogRules")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(15L)  + question_emoji).callbackData("dogDocs")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(16L)  + question_emoji).callbackData("dogRecsTransportation")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(17L)  + question_emoji).callbackData("dogRecsHomePuppy")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(18L)  + question_emoji).callbackData("dogRecsHomeAdult")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(19L)  + question_emoji).callbackData("dogRecsDisabled")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(20L)  + question_emoji).callbackData("dogRecsDisabled")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(22L)  + question_emoji).callbackData("dogRecsDisabled")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(8L)  + pencil_emoji).callbackData("dogClientContacts")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(7L)  + man_emoji).callbackData("callVolunteer")}})));
    }

    private void dogReport(Update update) {
        SendMessage messageText = new SendMessage(update.message().chat().id(),  "Ведение питомца:").parseMode(ParseMode.HTML);
        InlineKeyboardButton dogFormReportInfo  = new InlineKeyboardButton("Форма ежедневного отчета ");
        InlineKeyboardButton dogSendPhoto = new InlineKeyboardButton("Прислать фото кошки");
        //Дальше обработка действий клиента при отправке отчета о собаке. Если отослал фото - проверка и
        // просьба отослать информацию о здоровье, питании и изменениях поведения
        // + напоминалка

        //кнопки -->
//        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
//                new InlineKeyboardButton[][]{{???.callbackData("???")},
//                        {???.callbackData("???")}});
//        messageText.replyMarkup(inlineKeyboard);
//        telegramBot.execute(messageText);
    }
    private String getQuestion(Long number) {
        return questionRepository.getQuestionById(number).getTextQuestion();
    }
    private String getParent(Long number) {
        Long parent = questionRepository.getParentById(number);
        return questionRepository.getQuestionById(parent).getTextQuestion();
    }


}
