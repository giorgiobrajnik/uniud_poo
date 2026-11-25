package it.uniud.tda.intranet;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Missione: esprimere il superamento di un corso con un voto minimo.
 * Stato astratto: corso richiesto e soglia inclusiva; il minimo è 18.
 */
@EqualsAndHashCode
@ToString
public final class Prerequisite {
    /* RI: courseCode e minimumGrade non null. AF: requisito corso >= voto. */
    private final CourseCode courseCode;
    private final Grade minimumGrade;

    /**
     * Crea un requisito di semplice superamento (almeno 18).
     * @param courseCode corso richiesto
     * @return requisito con soglia 18
     * @throws NullPointerException se courseCode è null
     * @post Il corso richiesto è courseCode e il minimo è 18.
     */
    public static Prerequisite passed(CourseCode courseCode) {
        return new Prerequisite(courseCode, Grade.of(18));
    }

    /**
     * Crea un requisito con soglia.
     * @param courseCode corso richiesto
     * @param minimumGrade voto minimo incluso
     * @throws NullPointerException se un argomento è null
     * @post Lo stato astratto coincide con gli argomenti.
     */
    public Prerequisite(CourseCode courseCode, Grade minimumGrade) {
        this.courseCode = Objects.requireNonNull(courseCode, "courseCode");
        this.minimumGrade = Objects.requireNonNull(minimumGrade, "minimumGrade");
    }

    /** @return il corso richiesto; @post This è invariato. */
    public CourseCode courseCode() { return courseCode; }

    /** @return il voto minimo; @post This è invariato. */
    public Grade minimumGrade() { return minimumGrade; }

    /**
     * Verifica il requisito delegando alla carriera.
     * @param career carriera da controllare
     * @return true se il corso è superato con voto almeno pari alla soglia
     * @throws NullPointerException se career è null
     * @post This e career sono invariati.
     */
    public boolean isSatisfiedBy(Career career) {
        return Objects.requireNonNull(career, "career").satisfies(this);
    }
}
