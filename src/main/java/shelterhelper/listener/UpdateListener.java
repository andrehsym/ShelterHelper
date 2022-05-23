package shelterhelper.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import shelterhelper.keyboards.InlineKeyboardsMaker;

////import org.telegram.telegrambots.ApiContextInitializer;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
////import org.telegram.telegrambots.meta.ApiContext;
//import org.telegram.telegrambots.meta.TelegramBotsApi;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
//import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;

public class UpdateListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(UpdateListener.class);
    private TelegramBot telegramBot;

    public UpdateListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (update.message().text().equals("/start")) {
                SendMessage messageText = new SendMessage(update.message().chat().id(),
                        "Приветствуем в нашем виртуальном помощнике! " + "\nВыберите интересующий пункт:");
                InlineKeyboardButton firstButton = new InlineKeyboardButton("Узнать информацию о приюте");
                InlineKeyboardButton secondButton = new InlineKeyboardButton("Как взять собаку из приюта");
                InlineKeyboardButton thirdButton = new InlineKeyboardButton("Прислать отчет о питомце ");
                InlineKeyboardButton fourthButton = new InlineKeyboardButton("Позвать волонтера");
                InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                        new InlineKeyboardButton[][]{{firstButton.callbackData("firstButton")},
                                {secondButton.callbackData("secondButton")},
                                {thirdButton.callbackData("thirdButton")},
                                {fourthButton.callbackData("fourthButton")}});
                messageText.replyMarkup(inlineKeyboard);
                telegramBot.execute(messageText);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void startMessage(Update update) {


//        SendMessage request = new SendMessage(update.message().chat().id(), "Привет! " +
//                "\nЧтобы создать напоминание, напиши: " +
//                "\nчисло.месяц.год точное:время текст напоминания" +
//                "\nНапример:" +
//                "\n05.01.2022 20:00 Сесть за домашнюю работу");
//        SendResponse sendResponse = telegramBot.execute(request);
    }

}
