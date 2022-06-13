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
                        /*Приветствие из БД + */questionRepository.getQuestionById(1L).getTextQuestion() + "\nВыберите приют: ")
                        .parseMode(ParseMode.HTML)
                        .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                                {{new InlineKeyboardButton("Приют для кошек" + cat_emoji).callbackData("catShelter")},
                                        {new InlineKeyboardButton("Приют для собак" + dog_emoji).callbackData("dogShelter")}})));
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
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), /*Приветствие из БД + */"Приют для кошек: ")
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton("Информация о приюте для кошек").callbackData("catShelterInfo")},
                {new InlineKeyboardButton("Как взять кошку из приюта").callbackData("catAdopt")},
                {new InlineKeyboardButton("Прислать отчет о кошке").callbackData("catReport")},
                {new InlineKeyboardButton("Позвать волонтера").callbackData("callVolunteer")}})));
    }

    private void catShelterInfo(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), /*Приветствие из БД + */"Информация о приюте для кошек: ")
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton("Информация о приюте для кошек").callbackData("catShelterAbout")},
                 {new InlineKeyboardButton("Время работы, адрес, схема проезда - приют для кошек").callbackData("catSheduleScheme")},
                 {new InlineKeyboardButton("Контакты для оформления пропуска").callbackData("catPass")},
                 {new InlineKeyboardButton("ТБ на территории приюта для кошек").callbackData("callPrevention")},
                 {new InlineKeyboardButton("Принять и записать ваши контактные данные для связи").callbackData("callClientContacts")},
                 {new InlineKeyboardButton("Позвать волонтера").callbackData("callVolunteer")}})));
    }

    private void catAdopt(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), /*Приветствие из БД + */"Как взять кошку из приюта: ")
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton("Правила знакомства с кошкой до того, как забрать её из приюта").callbackData("catRules")},
                 {new InlineKeyboardButton("Документы, необходимые, чтобы взять кошку из приюта").callbackData("catDocs")},
                 {new InlineKeyboardButton("Рекомендации по транспортировке кошки").callbackData("catRecsTransportation")},
                 {new InlineKeyboardButton("Рекомендации по обустройству дома для котенка").callbackData("catRecsHomeKitty")},
                 {new InlineKeyboardButton("Рекомендации по обустройству дома для взрослой кошки").callbackData("catRecsHomeAdult")},
                 {new InlineKeyboardButton("Рекомендации по обустройству дома для кошки с ограниченными возможностями").callbackData("catRecsDisabled")},
                 {new InlineKeyboardButton("Список причин отказа в заборе кошки из приюта").callbackData("catRejectionReasons")},
                 {new InlineKeyboardButton("Принять и записать ваши контактные данные для связи").callbackData("catClientContacts")},
                 {new InlineKeyboardButton("Позвать волонтера").callbackData("callVolunteer")}})));
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
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), /*Приветствие из БД + */"Приют для собак: ")
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton("Информация о приюте для собак").callbackData("dogShelterInfo")},
                 {new InlineKeyboardButton("Как взять собаку из приюта").callbackData("dogAdopt")},
                 {new InlineKeyboardButton("Прислать отчет о собаке").callbackData("dogReport")},
                 {new InlineKeyboardButton("Позвать волонтера").callbackData("callVolunteer")}})));
    }

    private void dogShelterInfo(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), /*Приветствие из БД + */"Информация о приюте для собак: ")
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton("Информация о приюте для собак").callbackData("dogShelterAbout")},
                 {new InlineKeyboardButton("Время работы, адрес, схема проезда - приют для собак").callbackData("dogSheduleScheme")},
                 {new InlineKeyboardButton("Контакты для оформления пропуска").callbackData("dogPass")},
                 {new InlineKeyboardButton("ТБ на территории приюта для собак").callbackData("dogPrevention")},
                 {new InlineKeyboardButton("Принять и записать ваши контактные данные для связи").callbackData("dogClientContacts")},
                 {new InlineKeyboardButton("Позвать волонтера").callbackData("callVolunteer")}})));
    }

    private void dogAdopt(Update update) {
        telegramBot.execute(new SendMessage(update.callbackQuery().from().id(), /*Приветствие из БД + */"Как взять собаку из приюта: ")
            .replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[][]
                {{new InlineKeyboardButton("Правила знакомства с собакой до того, как забрать её из приюта").callbackData("dogRules")},
                 {new InlineKeyboardButton("Документы, необходимые, чтобы взять собаку из приюта").callbackData("dogDocs")},
                 {new InlineKeyboardButton("Рекомендации по транспортировке собаки").callbackData("dogRecsTransportation")},
                 {new InlineKeyboardButton("Рекомендации по обустройству дома для щенка").callbackData("dogRecsHomePuppy")},
                 {new InlineKeyboardButton("Рекомендации по обустройству дома для взрослой собаки").callbackData("dogRecsHomeAdult")},
                 {new InlineKeyboardButton("Рекомендации по обустройству дома для собаки с ограниченными возможностями").callbackData("dogRecsDisabled")},
                 {new InlineKeyboardButton("Советы кинолога по первичному общению с собакой").callbackData("dogRecsDisabled")},
                 {new InlineKeyboardButton("Рекомендации по проверенным кинологам").callbackData("dogRecsDisabled")},
                 {new InlineKeyboardButton("Список причин отказа в заборе собаки из приюта").callbackData("dogRejectionReasons")},
                 {new InlineKeyboardButton("Принять и записать ваши контактные данные для связи").callbackData("dogClientContacts")},
                 {new InlineKeyboardButton("Позвать волонтера").callbackData("callVolunteer")}})));
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
}
