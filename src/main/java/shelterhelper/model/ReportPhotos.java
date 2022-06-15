package shelterhelper.model;


import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "report_photos")
public class ReportPhotos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_photo", unique = true)
    private long idPhoto;
    @Column(name = "file_size")
    private long fileSize;
    @Column(name = "media_type")
    private String mediaType;
    @Lob
    @Column(name = "data")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "id_report")
    private Reports reports;

    public long getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(long idPhoto) {
        this.idPhoto = idPhoto;
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

    public Reports getReports() {
        return reports;
    }

    public void setReports(Reports reports) {
        this.reports = reports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportPhotos that = (ReportPhotos) o;
        return idPhoto == that.idPhoto && fileSize == that.fileSize && mediaType.equals(that.mediaType) && Arrays.equals(data, that.data) && reports.equals(that.reports);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(idPhoto, fileSize, mediaType, reports);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "ReportPhotos{" +
                "idPhoto=" + idPhoto +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", reports=" + reports +
                '}';
    }
}
