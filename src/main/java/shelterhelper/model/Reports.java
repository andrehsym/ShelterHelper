package shelterhelper.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Таблица отчетов - для хранения отчетов и работы с ними волонтеров.
 * Таблица отчетов связана с таблицей report_photos, в которой храняться
 * фотографии питомцев, присылаемые вместе с отчетом. Каждый отчет должен
 * содержать одну или несколько фотографий. Поступая из telegramBot, отчеты
 * записываются в таблицу reports, а фотографии в таблицу report_photos.
 * private Long idPet;    идентификатор
 * idEntity - cущность ( кот, собака, и запись - по умолчанию, нужна для настроек меню
 * private Long petName;  имя
 * private String petText; описание
 * private boolean isUsed;  уже усыновлен
 */
@Entity
@Table(name = "reports")

public class Reports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_report", unique = true)
    public Long idReport;
    @Column(name = "id_user")
    public Long idUser;
    @Column(name = "id_pet")
    public Long idPet;
    @Column(name = "is_checked")
    public boolean isChecked;
    @Column(name = "is_adopter")
    public boolean isAdopter;
    @Column(name = "date_Probation")
    public LocalDate dateProbation;
    @OneToMany(mappedBy = "reports", cascade=CascadeType.ALL)
    @JsonIgnore
    private Collection<ReportPhotos> reportPhotos;
}
