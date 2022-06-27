package shelterhelper.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

/**
 * Таблица отчетов - для хранения отчетов и работы с ними волонтеров.
 * Таблица отчетов связана с таблицей report_photos, в которой храняться
 * фотографии питомцев, присылаемые вместе с отчетом. Ещё таблица отчетов
 * связана с таблицей усыновителей adopted_pets по id усыновителя.
 * Каждый отчет содержит текст отчета и одну или несколько фотографий.
 * Поступая из telegramBot, текст отчета записывается в таблицу reports,
 * а фотографии в таблицу report_photos.
 * idReport - идентификатор отчета;
 * dateReport - дата отчета (проставляется автоматически текущая);
 * textReport - текст отчета (ограничен 900 знаков);
 * isAccepted - флаг проверки отчета: отчет принят - true, не принят - false (default - false);
 * id - идентификатор усыновителя - связь с таблицей adopted_pets.
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

    public Reports(Long idReport, LocalDate dateReport, String textReport, boolean isAccepted) {
        this.idReport = idReport;
        this.dateReport = dateReport;
        this.textReport = textReport;
        this.isAccepted = isAccepted;
    }

    public Reports() {
    }

    public Long getIdReport() {
        return idReport;
    }

    public void setIdReport(Long idReport) {
        this.idReport = idReport;
    }

    public LocalDate getDateReport() {
        return dateReport;
    }

    public void setDateReport(LocalDate dateReport) {
        this.dateReport = dateReport;
    }

    public String getTextReport() {
        return textReport;
    }

    public void setTextReport(String textReport) {
        this.textReport = textReport;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public AdoptedPets getAdoptedPets() {
        return adoptedPets;
    }

    public void setAdoptedPets(AdoptedPets adoptedPets) {
        this.adoptedPets = adoptedPets;
    }

    public Collection<ReportPhotos> getReportPhotos() {
        return reportPhotos;
    }

    public void setReportPhotos(Collection<ReportPhotos> reportPhotos) {
        this.reportPhotos = reportPhotos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reports reports = (Reports) o;
        return isAccepted == reports.isAccepted && idReport.equals(reports.idReport) && dateReport.equals(reports.dateReport) && Objects.equals(textReport, reports.textReport) && adoptedPets.equals(reports.adoptedPets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReport, dateReport, textReport, isAccepted, adoptedPets);
    }
}
