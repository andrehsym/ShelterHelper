package shelterhelper.model;


import javax.persistence.*;

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

}
