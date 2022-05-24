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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UpdateListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(UpdateListener.class);
    private TelegramBot telegramBot;

    public UpdateListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
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

    private void startMethod(Update update) {
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

}
