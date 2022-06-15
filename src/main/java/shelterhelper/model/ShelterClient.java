package shelterhelper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * Создать сущность ShelterClient
 * Цель задачи — получить класс с аннотацией @Entity,
 * который будет повторять структуру таблицу пользователей в БД
 *     private Long idUser;   идентификатор пользователя - ключ для связи с остальными БД
 *     private Long idChat;   идентификатор чата
 *     private String nameUserInChat;   имя пользователя в чате
 *     private LocalDateTime stamp;
 *     private String emailUser;
 *     private String phoneUser;
 *     private Long idRequest;         идентификатор последнего запроса
 *     private boolean isAdopt;        является на данный момент усыновителем?
 *
 **/
@Entity
public class ShelterClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    private Long idChat;
    private String nameUserInChat;
    private LocalDateTime stamp;
    private String emailUser;
    private String phoneUser;
    @Column(name = "id_question")
    private Long idQuestion;
    private boolean isAdopt;
    @OneToMany(mappedBy = "shelterClient", cascade=CascadeType.ALL)
    @JsonIgnore
    private Collection<Reports> reports;

    public ShelterClient(Long idUser, Long idChat, String nameUserInChat, LocalDateTime stamp, String emailUser, String phoneUser, Long idQuestion, boolean isAdopt) {
        this.idUser = idUser;
        this.idChat = idChat;
        this.nameUserInChat = nameUserInChat;
        this.stamp = stamp;
        this.emailUser = emailUser;
        this.phoneUser = phoneUser;
        this.idQuestion = idQuestion;
        this.isAdopt = isAdopt;
    }

    public ShelterClient() {
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
        return idQuestion;
    }

    public void setIdRequest(Long idRequest) {
        this.idQuestion = idRequest;
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
        ShelterClient shelterClient = (ShelterClient) o;
        return isAdopt == shelterClient.isAdopt && Objects.equals(idUser, shelterClient.idUser) && Objects.equals(idChat, shelterClient.idChat) && Objects.equals(nameUserInChat, shelterClient.nameUserInChat) && Objects.equals(stamp, shelterClient.stamp) && Objects.equals(emailUser, shelterClient.emailUser) && Objects.equals(phoneUser, shelterClient.phoneUser) && Objects.equals(idQuestion, shelterClient.idQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser);
    }

    @Override
    public String toString() {
        return "ShelterClient{" +
                "idUser=" + idUser +
                ", idChat=" + idChat +
                ", nameUserInChat='" + nameUserInChat + '\'' +
                ", stamp=" + stamp +
                ", emailUser='" + emailUser + '\'' +
                ", phoneUser='" + phoneUser + '\'' +
                ", idRequest=" + idQuestion +
                ", isAdopt=" + isAdopt +
                '}';
    }
}
