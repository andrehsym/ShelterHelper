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
 * idReport - идентификатор отчета;
 * dateReport - дата отчета (проставляется автоматически текущая);
 * textReport - текст отчета (ограничен 900 знаков);
 * isAccepted - флаг проверки отчета: отчет принят - true, не принят - false (default - false).
 */
@Entity
@Table(name = "reports")

public class Reports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_report", unique = true)
    public Long idReport;
    @Column(name = "date_report")
    public LocalDate dateReport;
    @Column(name = "text_report")
    public String textReport;
    @Column(name = "is_accepted")
    public boolean isAccepted;

    @ManyToOne
    @JoinColumn(name = "id")
    private AdoptedPets adoptedPets;
    @OneToMany(mappedBy = "reports", cascade=CascadeType.ALL)
    @JsonIgnore
    private Collection<ReportPhotos> reportPhotos;
}
