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
import shelterhelper.service.AnswerService;
import shelterhelper.service.Contacts;

import javax.annotation.PostConstruct;
import java.util.List;

import static shelterhelper.model.Constants.*;

@Service
public class UpdateListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(UpdateListener.class);
    private final TelegramBot telegramBot;
    private final QuestionRepository questionRepository;
    private final Contacts contacts;
    private final AnswerService answerService;


    private final String cat_emoji = EmojiParser.parseToUnicode(EMOJI_CAT);
    private final String dog_emoji = EmojiParser.parseToUnicode(EMOJI_DOG);
    private final String question_emoji = EmojiParser.parseToUnicode(EMOJI_QUESTION);
    private final String man_emoji = EmojiParser.parseToUnicode(EMOJI_PERSON);
    private final String pencil_emoji = EmojiParser.parseToUnicode(EMOJI_PENCIL);
    private static String emoji = null;

    public UpdateListener(TelegramBot telegramBot, QuestionRepository questionRepository, Contacts contacts, AnswerService answerService) {
        this.telegramBot = telegramBot;
        this.questionRepository = questionRepository;
        this.contacts = contacts;
        this.answerService = answerService;
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
     * текстовые значения получаем из БД вопросов
     * информацию - БД answer
     *
     * @param update параметр
     */

    private void startMethod(Update update) {
        try {
            if (update.message().text().equals("/start")) {
                telegramBot.execute(new SendMessage(update.message().chat().id(), getQuestion(1L) + "\nВыберите приют: ")
                        .parseMode(ParseMode.HTML)
                        .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                                {{new InlineKeyboardButton(getQuestion(2L) + cat_emoji).callbackData("catShelter")},
                                 {new InlineKeyboardButton(getQuestion(3L) + dog_emoji).callbackData("dogShelter")}})));
            }
        } catch (NullPointerException e) {
            logger.info("Exception: {}", e + " in startMethod");
        }
    }

    private void checkingCallbackQuery(Update update) {
        try {
            if (update.callbackQuery().data().startsWith("call")) {
                checkingCall(update);
            } else {
                checkingCallbackQueryPet(update);
            }
        } catch (NullPointerException e) {
            logger.info("Exception: {}", e + " in checkingCallbackQuery method");
        }
    }

    private void checkingCallbackQueryPet(Update update) {
        Long chatId = update.callbackQuery().message().chat().id();
        switch (update.callbackQuery().data()) {
            case "catShelter":
                emoji = cat_emoji;
                catShelter(update);
                break;
            case "dogShelter":
                emoji = dog_emoji;
                dogShelter(update);
                break;
            case "petShelterInfo":
                petShelterInfo(update, emoji);
                break;
            case "dogAdopt":
                dogAdopt(update);
                break;
            case "catAdopt":
                catAdopt(update);
                break;
            case "petReport":
                petReport(update, emoji);
                break;
            case "petShelterAbout":
                sendTextMessage(chatId, 8L);
                break;
            case "petScheduleScheme":
                sendTextMessage(chatId, 9L);
                break;
            case "petPass":
                sendTextMessage(chatId, 10L);
                break;
            case "petPrevention":
                sendTextMessage(chatId, 11L);
                break;
            case "petRules":
                sendTextMessage(chatId, 14L);
                break;
            case "petDocs":
                sendTextMessage(chatId, 15L);
                break;
            case "petRecsTransportation":
                sendTextMessage(chatId, 16L);
                break;
            case "petRecsHomePuppy":
                sendTextMessage(chatId, 17L);
                break;
            case "petRecsHomeAdult":
                sendTextMessage(chatId, 18L);
                break;
            case "petRecsHomeDisabled":
                sendTextMessage(chatId, 19L);
                break;
            case "petRejectionReasons":
                sendTextMessage(chatId, 22L);
                break;
            case "petCytologistAdvice":
                sendTextMessage(chatId, 20L);
                break;
            case "petRecsCytologist":
                sendTextMessage(chatId, 21L);
                break;
        }
    }


    private void checkingCall(Update update) {
        switch (update.callbackQuery().data()) {
            case "callClientContacts":
                telegramBot.execute(new SendMessage(update.message().chat().id(), "Введите контакт в формате" +
                        "\nЭлектронная почта и номер телефона, начинающийся с цифры 8 без пробелов/скобок/тире." +
                        "\nНапример:" +
                        "\nmariya@yandex.ru 89991116655"));
                contacts.callClientContacts(update);
                break;
            case "callVolunteer":
                callVolunteer(update);
                break;
        }
    }

    private void callVolunteer(Update update) { //создать метод для вызова волонтера

    }

    private void catShelter(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), getQuestion(2L))
                .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                               {{new InlineKeyboardButton(cat_emoji + getQuestion(4L) + question_emoji).callbackData("petShelterInfo")},
                                {new InlineKeyboardButton(cat_emoji + getQuestion(5L) + question_emoji).callbackData("catAdopt")},
                                {new InlineKeyboardButton(cat_emoji + getQuestion(6L) + pencil_emoji).callbackData("petReport")},
                                {new InlineKeyboardButton(cat_emoji + getQuestion(7L) + man_emoji).callbackData("callVolunteer")}})));
    }

    private void catAdopt(Update update) { //ЭТАП 2
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), getQuestion(5L) + cat_emoji)
                .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                        {{new InlineKeyboardButton(cat_emoji + getQuestion(14L) + question_emoji).callbackData("petRules")},
                                {new InlineKeyboardButton(cat_emoji + getQuestion(15L) + question_emoji).callbackData("petDocs")},
                                {new InlineKeyboardButton(cat_emoji + getQuestion(16L) + question_emoji).callbackData("petRecsTransportation")},
                                {new InlineKeyboardButton(cat_emoji + getQuestion(17L) + question_emoji).callbackData("petRecsHomeKitty")},
                                {new InlineKeyboardButton(cat_emoji + getQuestion(18L) + question_emoji).callbackData("petRecsHomeAdult")},
                                {new InlineKeyboardButton(cat_emoji + getQuestion(19L) + question_emoji).callbackData("petRecsDisabled")},
                                {new InlineKeyboardButton(cat_emoji + getQuestion(22L) + question_emoji).callbackData("petRejectionReasons")},
                                {new InlineKeyboardButton(cat_emoji + getQuestion(23L) + pencil_emoji).callbackData("callClientContacts")},
                                {new InlineKeyboardButton(cat_emoji + getQuestion(7L) + man_emoji).callbackData("callVolunteer")}})));
    }

    private void dogShelter(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), /*Приветствие из БД + */ getQuestion(3L))
                .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                               {{new InlineKeyboardButton(dog_emoji + getQuestion(4L) + question_emoji).callbackData("petShelterInfo")},
                                {new InlineKeyboardButton(dog_emoji + getQuestion(5L) + question_emoji).callbackData("dogAdopt")},
                                {new InlineKeyboardButton(dog_emoji + getQuestion(6L) + pencil_emoji).callbackData("petReport")},
                                {new InlineKeyboardButton(dog_emoji + getQuestion(7L) + man_emoji).callbackData("callVolunteer")}})));
    }

    private void petShelterInfo(Update update, String emoji) { //ЭТАП 1
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(),emoji + getQuestion(4L))
                .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                               {{new InlineKeyboardButton(emoji + getQuestion(8L)  + question_emoji).callbackData("petShelterAbout")},
                                {new InlineKeyboardButton(emoji + getQuestion(9L)  + question_emoji).callbackData("petScheduleScheme")},
                                {new InlineKeyboardButton(emoji + getQuestion(10L) + question_emoji).callbackData("petPass")},
                                {new InlineKeyboardButton(emoji + getQuestion(11L) + question_emoji).callbackData("petPrevention")},
                                {new InlineKeyboardButton(emoji + getQuestion(12L) + pencil_emoji).callbackData("callClientContacts")},
                                {new InlineKeyboardButton(emoji + getQuestion(7L)  + man_emoji).callbackData("callVolunteer")}})));
    }

    private void dogAdopt(Update update) { //ЭТАП 2
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), getQuestion(5L) + dog_emoji)
                .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                               {{new InlineKeyboardButton(dog_emoji + getQuestion(14L) + question_emoji).callbackData("petRules")},
                                {new InlineKeyboardButton(dog_emoji + getQuestion(15L) + question_emoji).callbackData("petDocs")},
                                {new InlineKeyboardButton(dog_emoji + getQuestion(16L) + question_emoji).callbackData("petRecsTransportation")},
                                {new InlineKeyboardButton(dog_emoji + getQuestion(17L) + question_emoji).callbackData("petRecsHomePuppy")},
                                {new InlineKeyboardButton(dog_emoji + getQuestion(18L) + question_emoji).callbackData("petRecsHomeAdult")},
                                {new InlineKeyboardButton(dog_emoji + getQuestion(19L) + question_emoji).callbackData("petRecsDisabled")},
                                {new InlineKeyboardButton(dog_emoji + getQuestion(20L) + question_emoji).callbackData("dogCytologistAdvice")},
                                {new InlineKeyboardButton(dog_emoji + getQuestion(21L) + question_emoji).callbackData("dogCytologist")},
                                {new InlineKeyboardButton(dog_emoji + getQuestion(22L) + question_emoji).callbackData("petRejectionReasons")},
                                {new InlineKeyboardButton(dog_emoji + getQuestion(23L) + pencil_emoji).callbackData("callClientContacts")},
                                {new InlineKeyboardButton(dog_emoji + getQuestion(7L) + man_emoji).callbackData("callVolunteer")}})));
    }

    private void petReport(Update update, String emoji) { //ЭТАП 3
        SendMessage messageText = new SendMessage(update.message().chat().id(), "Ведение питомца:").parseMode(ParseMode.HTML);
        InlineKeyboardButton dogFormReportInfo = new InlineKeyboardButton("Форма ежедневного отчета ");
        InlineKeyboardButton dogSendPhoto = new InlineKeyboardButton("Прислать фото" + emoji);
        //Дальше обработка действий клиента при отправке отчета о собаке. Если отослал фото - проверка и
        // просьба отослать информацию о здоровье, питании и изменениях поведения
        // + напоминалка
    }

    private String getQuestion(Long number) {
        return questionRepository.getQuestionById(number).getTextQuestion();
    }

    private void sendTextMessage(Long chatId, Long number) {
        SendMessage request = new SendMessage(chatId, answerService.getAnswer(number));
        telegramBot.execute(request);
    }
}
