package shelterhelper.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Создать сущность user
 * Цель задачи — получить класс с аннотацией @Entity,
 * который будет повторять структуру таблицу пользователей в БД
 * и будет пригоден для использования в коде нашего приложения.
 **/
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private Long idChat;
    private String nameUserInChat;
    private LocalDateTime stamp;
    private String emailUser;
    private String phoneUser;
    private Long idRequest;
    private boolean isAdopt;

    public User(Long idUser, Long idChat, String nameUserInChat,  LocalDateTime stamp, String emailUser, String phoneUser, Long idRequest, boolean isAdopt) {
        this.idUser = idUser;
        this.idChat = idChat;
        this.nameUserInChat = nameUserInChat;
        this.stamp = stamp;
        this.emailUser = emailUser;
        this.phoneUser = phoneUser;
        this.idRequest = idRequest;
        this.isAdopt = isAdopt;
    }

    public User() {
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public String getNameUserInChat() {
        return nameUserInChat;
    }

    public void setNameUserInChat(String nameUserInChat) {
        this.nameUserInChat = nameUserInChat;
    }

    public LocalDateTime getStamp() {
        return stamp;
    }

    public void setStamp(LocalDateTime stamp) {
        this.stamp = stamp;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public Long getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Long idRequest) {
        this.idRequest = idRequest;
    }

    public boolean isAdopt() {
        return isAdopt;
    }

    public void setAdopt(boolean adopt) {
        isAdopt = adopt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isAdopt == user.isAdopt && Objects.equals(idUser, user.idUser) && Objects.equals(idChat, user.idChat) && Objects.equals(nameUserInChat, user.nameUserInChat) && Objects.equals(stamp, user.stamp) && Objects.equals(emailUser, user.emailUser) && Objects.equals(phoneUser, user.phoneUser) && Objects.equals(idRequest, user.idRequest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser);
    }
}
