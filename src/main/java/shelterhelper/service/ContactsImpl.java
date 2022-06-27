package shelterhelper.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import shelterhelper.model.ShelterClient;
import shelterhelper.repository.ShelterClientRepository;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static shelterhelper.model.Constants.*;

@Service
public class ContactsImpl implements Contacts{
    private final ShelterClientRepository shelterClientRepository;
    private final TelegramBot telegramBot;

    public ContactsImpl(ShelterClientRepository shelterClientRepository, TelegramBot telegramBot) {
        this.shelterClientRepository = shelterClientRepository;
        this.telegramBot = telegramBot;
    }

    @Override
    public void callClientContacts(Update update) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(update.message().text());
        try {
            matcher.matches();
            String email = matcher.group(1);
            String phoneNumber = matcher.group(4);
            LocalDateTime stamp = LocalDateTime.now();
            ShelterClient newbie = new ShelterClient();
            newbie.setIdChat(update.message().chat().id());
            newbie.setNameUserInChat(update.message().from().firstName()+" "+update.message().from().lastName()+" "+update.message().from().username());
            newbie.setStamp(stamp);
            newbie.setPhoneUser(phoneNumber);
            newbie.setEmailUser(email);
            shelterClientRepository.save(newbie);
            telegramBot.execute(new SendMessage(update.message().chat().id(), "Ваши контакты для связи переданы"));
        } catch (IllegalStateException e) {
            telegramBot.execute(new SendMessage(update.message().chat().id(), "Неправильный формат контактов"));
        }
    }
}
