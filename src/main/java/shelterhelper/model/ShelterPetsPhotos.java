package shelterhelper.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "pets_photos")
public class ShelterPetsPhotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPhoto;
    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "id_pet")
    private ShelterPets shelterPets;

    public long getId_photo() {
        return idPhoto;
    }

    public void setId_photo(long id_photo) {
        this.idPhoto = id_photo;
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

    public ShelterPets getShelterPets() {
        return shelterPets;
    }

    public void setShelterPets(ShelterPets shelterPets) {
        this.shelterPets = shelterPets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShelterPetsPhotos that = (ShelterPetsPhotos) o;
        return idPhoto == that.idPhoto && fileSize == that.fileSize && mediaType.equals(that.mediaType) && Arrays.equals(data, that.data) && shelterPets.equals(that.shelterPets);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(idPhoto, fileSize, mediaType, shelterPets);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "ShelterPetsPhotos{" +
                "id_photo=" + idPhoto +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", shelterPets=" + shelterPets +
                '}';
    }
}
