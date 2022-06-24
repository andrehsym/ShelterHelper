package shelterhelper.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import shelterhelper.model.ShelterClient;
import shelterhelper.repository.QuestionRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                telegramBot.execute(new SendMessage(update.message().chat().id(), getQuestion(1L) + "\nВыберите приют: ")
                        .parseMode(ParseMode.HTML)
                        .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                                {{new InlineKeyboardButton("Приют для кошек" + cat_emoji).callbackData("catShelter")},
                                        {new InlineKeyboardButton("Приют для собак"  + dog_emoji).callbackData("dogShelter")}})));
            }
        } catch (NullPointerException e) {
            logger.info("Exception: {}", e + " in startMethod");
        }
    }

    private void checkingCallbackQuery(Update update) {
        try {
            if (update.callbackQuery().data().startsWith("cat")) {
                checkingCallbackQueryCat(update);
            } else if (update.callbackQuery().data().startsWith("dog")) {
                checkingCallbackQueryDog(update);
            } else if (update.callbackQuery().data().startsWith("call")) {
                checkingCall(update);
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
//            case "dogSheduleScheme":
//                dogSheduleScheme(update);
//                break;
//            case "dogPass":
//                dogPass(update);
//                break;
//            case "dogPrevention":
//                dogPrevention(update);
//                break;
//            case "dogRulesDating":
//                dogRulesDating(update);
//                break;
//            case "dogDocs":
//                dogDocs(update);
//                break;
//            case "dogRecsTransportation":
//                dogRecsTransportation(update);
//                break;
//            case "dogRecsHomePuppy":
//                dogRecsHomePuppy(update);
//                break;
//            case "dogRecsHomeAdult":
//                dogRecsHomeAdult(update);
//                break;
//            case "dogRecsHomeDisbled":
//                dogRecsHomeDisbled(update);
//                break;
//            case "dogRejectionReasons":
//                dogRejectionReasons(update);
//                break;
//            case "dogCinologistAdvice":
//                dogCinologistAdvice(update);
//                break;
//            case "dogRecsCinologist":
//                dogRecsCinologist(update);
//                break;
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
//            case "catSheduleScheme":
//                catSheduleScheme(update);
//                break;
//            case "catPass":
//                catPass(update);
//                break;
//            case "catPrevention":
//                catPrevention(update);
//                break;
//            case "catRulesDating":
//                catRulesDating(update);
//                break;
//            case "catDocs":
//                catDocs(update);
//                break;
//            case "catRecsTransportation":
//                catRecsTransportation(update);
//                break;
//            case "catRecsHomeKitty":
//                catRecsHomeKitty(update);
//                break;
//            case "catRecsHomeAdult":
//                catRecsHomeAdult(update);
//                break;
//            case "catRecsHomeDisbled":
//                catRecsHomeDisbled(update);
//                break;
//            case "catRejectionReasons":
//                catRejectionReasons(update);
//                break;
        }
    }

    private void checkingCall(Update update) {
        switch (update.callbackQuery().data()) {
            case "callClientContacts":
                dogShelter(update);
                break;
            case "callVolunteer":
                callVolunteer(update);
                break;
        }
    }

    private void callVolunteer(Update update) { //создать метод для вызова волонтера

    }

    private void callClientContacts(Update update) {
//        Pattern pattern = Pattern.compile("([\\W+]+)(\\s)(^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$)(\\s)([\"^\\\\d{10}$\"]{12})"); //паттерн с именем клиента, которое он вводит - не ник в телеграме
        Pattern pattern = Pattern.compile("(^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$)(\\s)([\"^\\\\d{10}$\"]{12})");
        Matcher matcher = pattern.matcher(update.message().text());
        try {
            matcher.matches();
//            String name = matcher.group(1); //имя клиента, которое он вводит
            String email = matcher.group(1);
            String phoneNumber = matcher.group(3);
            LocalDateTime stamp = LocalDateTime.now();
            ShelterClient newbie = new ShelterClient();
            newbie.setIdChat(update.message().chat().id());
            newbie.setNameUserInChat(update.chatMember().from().username());
            newbie.setStamp(stamp);
            newbie.setPhoneUser(phoneNumber);
            newbie.setEmailUser(email);
//            shelterClientRepository.save(newbie); //создать репозиторий ShelterClient
            telegramBot.execute(new SendMessage(update.message().chat().id(), "Ваши контакты для связи переданы"));
        } catch (IllegalStateException e) {
            telegramBot.execute(new SendMessage(update.message().chat().id(), "Неправильный формат"));;
        }
    }

    private void catShelter(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), getQuestion(2L) )
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton(cat_emoji + getQuestion(6L) + question_emoji ).callbackData("catShelterInfo")},
                 {new InlineKeyboardButton(cat_emoji + "Как взять кошку из приюта" + question_emoji ).callbackData("catAdopt")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(4L) + pencil_emoji ).callbackData("catReport")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(5L) + man_emoji  ).callbackData("callVolunteer")}})));
    }

    private void catShelterInfo(Update update) { //ЭТАП 1
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), getQuestion(4L))
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton(cat_emoji + getQuestion(2L)  + question_emoji).callbackData("catShelterAbout")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(7L)  + question_emoji).callbackData("catSheduleScheme")},
                 {new InlineKeyboardButton(cat_emoji + "Контакты охраны для пропуска на машину" + question_emoji).callbackData("catPass")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(8L) + pencil_emoji ).callbackData("catReport")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(12L) + pencil_emoji).callbackData("callClientContacts")},
                 {new InlineKeyboardButton(cat_emoji + getQuestion(5L)  + man_emoji).callbackData("callVolunteer")}})));
    }

    private void catAdopt(Update update) { //ЭТАП 2
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

    private void catReport(Update update) { //ЭТАП 3
        SendMessage messageText = new SendMessage(update.message().chat().id(),  "Ведение питомца:").parseMode(ParseMode.HTML);
        InlineKeyboardButton catFormReportInfo  = new InlineKeyboardButton("Форма ежедневного отчета ");
        InlineKeyboardButton catSendPhoto = new InlineKeyboardButton("Прислать фото кошки");
        //Дальше обработка действий клиента при отправке отчета о кошке. Если отослал фото - проверка и
        // просьба отослать информацию о здоровье, питании и изменениях поведения
        // + напоминалка
    }

    private void dogShelter(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), /*Приветствие из БД + */ getQuestion(3L) )
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton(dog_emoji + getQuestion(4L) + question_emoji).callbackData("dogShelterInfo")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(5L) + question_emoji).callbackData("dogAdopt")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(6L) + pencil_emoji).callbackData("dogReport")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(7L) + man_emoji).callbackData("callVolunteer")}})));
    }

    private void dogShelterInfo(Update update) { //ЭТАП 1
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), dog_emoji + getQuestion(4L))
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton(dog_emoji + getQuestion(2L)  + question_emoji).callbackData("dogShelterAbout")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(7L)  + question_emoji).callbackData("dogSheduleScheme")},
                 {new InlineKeyboardButton(cat_emoji + "Контакты охраны для пропуска на машину" + question_emoji).callbackData("dogPass")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(8L) + question_emoji).callbackData("dogPrevention")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(12L) + pencil_emoji).callbackData("callClientContacts")},
                 {new InlineKeyboardButton(dog_emoji + getQuestion(10L)  + man_emoji).callbackData("callVolunteer")}})));
    }

    private void dogAdopt(Update update) { //ЭТАП 2
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

    private void dogReport(Update update) { //ЭТАП 3
        SendMessage messageText = new SendMessage(update.message().chat().id(),  "Ведение питомца:").parseMode(ParseMode.HTML);
        InlineKeyboardButton dogFormReportInfo  = new InlineKeyboardButton("Форма ежедневного отчета ");
        InlineKeyboardButton dogSendPhoto = new InlineKeyboardButton("Прислать фото кошки");
        //Дальше обработка действий клиента при отправке отчета о собаке. Если отослал фото - проверка и
        // просьба отослать информацию о здоровье, питании и изменениях поведения
        // + напоминалка
    }
    private String getQuestion(Long number) {
        return questionRepository.getQuestionById(number).getTextQuestion();
    }
    private String getParent(Long number) {
        Long parent = questionRepository.getParentById(number);
        return questionRepository.getQuestionById(parent).getTextQuestion();
    }
}
