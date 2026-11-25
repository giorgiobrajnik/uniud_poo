package it.uniud.tda.intranet;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Missione: rappresentare il fatto immutabile che un corso è stato superato.
 * Stato astratto: corso, CFU, voto e data di verbalizzazione.
 */
@EqualsAndHashCode
@ToString
public final class ExamRecord {
    /* RI: tutti i campi non null. AF: la quadrupla esame verbalizzato. */
    private final CourseCode courseCode;
    private final CreditUnits credits;
    private final Grade grade;
    private final LocalDate recordedOn;

    /**
     * Crea un esito verbalizzato.
     * @param courseCode corso
     * @param credits peso in CFU
     * @param grade voto
     * @param recordedOn data di verbalizzazione
     * @throws NullPointerException se un argomento è null
     * @post Lo stato astratto coincide con gli argomenti.
     */
    public ExamRecord(CourseCode courseCode, CreditUnits credits, Grade grade,
                      LocalDate recordedOn) {
        this.courseCode = Objects.requireNonNull(courseCode, "courseCode");
        this.credits = Objects.requireNonNull(credits, "credits");
        this.grade = Objects.requireNonNull(grade, "grade");
        this.recordedOn = Objects.requireNonNull(recordedOn, "recordedOn");
    }

    /** @return il corso; @post This è invariato. */
    public CourseCode courseCode() { return courseCode; }

    /** @return i CFU; @post This è invariato. */
    public CreditUnits credits() { return credits; }

    /** @return il voto; @post This è invariato. */
    public Grade grade() { return grade; }

    /** @return la data di verbalizzazione; @post This è invariato. */
    public LocalDate recordedOn() { return recordedOn; }
}
