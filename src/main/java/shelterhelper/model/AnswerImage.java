package shelterhelper.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "answer_image")
public class AnswerImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] data;

    @OneToOne (cascade=CascadeType.ALL)
    private Answer answer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerImage that = (AnswerImage) o;
        return id == that.id && fileSize == that.fileSize && mediaType.equals(that.mediaType) && Arrays.equals(data, that.data) && answer.equals(that.answer);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, fileSize, mediaType, answer);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "AnswerImage{" +
                "id=" + id +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", answer=" + answer +
                '}';
    }

}
