package shelterhelper.service;

import com.pengrad.telegrambot.model.Update;
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

    public ContactsImpl(ShelterClientRepository shelterClientRepository) {
        this.shelterClientRepository = shelterClientRepository;
    }

    @Override
    public void callClientContacts(Update update) {
        //        Pattern pattern = Pattern.compile("([\\W+]+)(\\s)(^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$)(\\s)([\"^\\\\d{10}$\"]{12})"); //паттерн с именем клиента, которое он вводит - не ник в телеграме
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
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
            shelterClientRepository.save(newbie); //создать репозиторий ShelterClient
//            telegramBot.execute(new SendMessage(update.message().chat().id(), "Ваши контакты для связи переданы"));
        } catch (IllegalStateException e) {
//            telegramBot.execute(new SendMessage(update.message().chat().id(), "Неправильный формат"));
        }
    }
}
