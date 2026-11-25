package it.uniud.tda.intranet;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Missione: descrivere un insegnamento, il suo peso e i suoi prerequisiti.
 * Invariante di classe: codice valido, titolo non vuoto, CFU validi e al più
 * un requisito per ciascun corso, mai il corso stesso.
 */
@EqualsAndHashCode
@ToString
public final class Course {
    /*
     * RI: campi non null; title non blank; prerequisites è una copia
     * non modificabile, senza null, duplicati di codice o auto-requisito.
     * AF: corso astratto = codice, titolo, CFU e insieme dei requisiti.
     */
    private final CourseCode code;
    private final String title;
    private final CreditUnits credits;
    private final List<Prerequisite> prerequisites;

    /**
     * Crea un insegnamento.
     * @param code codice
     * @param title titolo
     * @param credits crediti
     * @param prerequisites requisiti
     * @throws NullPointerException se un argomento o requisito è null
     * @throws IllegalArgumentException per titolo vuoto, requisito duplicato o circolare diretto
     * @post Lo stato coincide con gli argomenti e la lista non è trattenuta.
     */
    public Course(CourseCode code, String title, CreditUnits credits,
                  List<Prerequisite> prerequisites) {
        this.code = Objects.requireNonNull(code, "code");
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Titolo vuoto");
        this.title = title.trim();
        this.credits = Objects.requireNonNull(credits, "credits");
        Objects.requireNonNull(prerequisites, "prerequisites");
        Set<CourseCode> seen = new HashSet<>();
        for (Prerequisite prerequisite : prerequisites) {
            Objects.requireNonNull(prerequisite, "prerequisite");
            if (code.equals(prerequisite.courseCode())) {
                throw new IllegalArgumentException("Un corso non può richiedere se stesso");
            }
            if (!seen.add(prerequisite.courseCode())) {
                throw new IllegalArgumentException("Prerequisito duplicato: " + prerequisite.courseCode());
            }
        }
        this.prerequisites = List.copyOf(prerequisites);
    }

    /** @return il codice; @post This è invariato. */
    public CourseCode code() { return code; }

    /** @return il titolo; @post This è invariato. */
    public String title() { return title; }

    /** @return i CFU; @post This è invariato. */
    public CreditUnits credits() { return credits; }

    /**
     * Osserva i prerequisiti.
     * @return fotografia non modificabile dei requisiti
     * @post Il risultato contiene esattamente i requisiti; this è invariato.
     */
    public List<Prerequisite> prerequisites() { return prerequisites; }

    /**
     * Calcola i requisiti non soddisfatti.
     * @param career carriera da valutare
     * @return lista non modificabile dei requisiti falsi
     * @throws NullPointerException se career è null
     * @post Result contiene esattamente i prerequisiti non soddisfatti; gli oggetti sono invariati.
     */
    public List<Prerequisite> missingPrerequisites(Career career) {
        Objects.requireNonNull(career, "career");
        java.util.ArrayList<Prerequisite> missing = new java.util.ArrayList<>();
        for (Prerequisite prerequisite : prerequisites) {
            if (!prerequisite.isSatisfiedBy(career)) missing.add(prerequisite);
        }
        return List.copyOf(missing);
    }
}
