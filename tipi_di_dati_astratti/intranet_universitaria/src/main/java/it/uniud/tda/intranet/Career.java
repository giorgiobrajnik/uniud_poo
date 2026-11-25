package it.uniud.tda.intranet;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Missione: governare gli esami superati da uno studente e calcolare crediti e media.
 * Stato astratto: titolare e funzione parziale da codici corso a esiti superati.
 * Invariante di classe: al più un esito per corso e ogni esito appartiene al suo codice.
 */
public final class Career {
    /*
     * RI: studentId e passedExams non null; nessuna chiave/valore null; ogni
     * chiave coincide con ExamRecord.courseCode(); la mappa non è esposta.
     * AF: studentId è il titolare; ogni codice associato a un record è un
     * esame superato.
     */
    private final StudentId studentId;
    private final Map<CourseCode, ExamRecord> passedExams = new HashMap<>();

    /**
     * Crea una carriera vuota.
     * @param studentId titolare
     * @throws NullPointerException se studentId è null
     * @post Il titolare è studentId e nessun corso risulta superato.
     */
    public Career(StudentId studentId) {
        this.studentId = Objects.requireNonNull(studentId, "studentId");
        checkRep();
    }

    /** @return il titolare; @post This è invariata. */
    public StudentId studentId() { return studentId; }

    /**
     * Verifica il superamento di un corso.
     * @param courseCode corso
     * @return true se e solo se esiste un esito per il corso
     * @throws NullPointerException se courseCode è null
     * @post This è invariata.
     */
    public boolean hasPassed(CourseCode courseCode) {
        return passedExams.containsKey(Objects.requireNonNull(courseCode, "courseCode"));
    }

    /**
     * Cerca un esito senza considerare l'assenza un errore.
     * @param courseCode corso
     * @return esito presente se e solo se il corso è stato superato
     * @throws NullPointerException se courseCode è null
     * @post This è invariata.
     */
    public Optional<ExamRecord> resultFor(CourseCode courseCode) {
        return Optional.ofNullable(passedExams.get(Objects.requireNonNull(courseCode, "courseCode")));
    }

    /**
     * Verbalizza un esame superato.
     * @param course corso superato
     * @param grade voto
     * @param recordedOn data di verbalizzazione
     * @throws NullPointerException se un argomento è null
     * @throws GradeRegistrationException se il corso è già verbalizzato
     * @post Il corso risulta superato col voto, CFU e data ricevuti; ogni altro
     *       esito e il titolare sono invariati.
     */
    void register(Course course, Grade grade, LocalDate recordedOn) {
        Objects.requireNonNull(course, "course");
        Objects.requireNonNull(grade, "grade");
        Objects.requireNonNull(recordedOn, "recordedOn");
        if (hasPassed(course.code())) {
            throw new GradeRegistrationException("Corso già verbalizzato: " + course.code().value());
        }
        passedExams.put(course.code(), new ExamRecord(course.code(), course.credits(), grade, recordedOn));
        checkRep();
    }

    /**
     * Verifica un prerequisito.
     * @param prerequisite requisito
     * @return true se il corso è superato con voto almeno pari alla soglia
     * @throws NullPointerException se prerequisite è null
     * @post This e prerequisite sono invariati.
     */
    public boolean satisfies(Prerequisite prerequisite) {
        Objects.requireNonNull(prerequisite, "prerequisite");
        ExamRecord record = passedExams.get(prerequisite.courseCode());
        return record != null && record.grade().isAtLeast(prerequisite.minimumGrade());
    }

    /**
     * Calcola i crediti acquisiti.
     * @return somma dei CFU di tutti gli esami superati
     * @post This è invariata.
     */
    public int earnedCredits() {
        int result = 0;
        for (ExamRecord record : passedExams.values()) result += record.credits().value();
        return result;
    }

    /**
     * Calcola la media dei voti pesata per CFU.
     * @return empty se la carriera è vuota; altrimenti somma(voto*CFU)/somma(CFU)
     * @post This è invariata; l'eventuale lode contribuisce numericamente come 30.
     */
    public OptionalDouble weightedAverage() {
        if (passedExams.isEmpty()) return OptionalDouble.empty();
        long weightedSum = 0;
        int credits = 0;
        for (ExamRecord record : passedExams.values()) {
            weightedSum += (long) record.grade().numericValue() * record.credits().value();
            credits += record.credits().value();
        }
        return OptionalDouble.of((double) weightedSum / credits);
    }

    /**
     * Osserva quanti corsi sono stati superati.
     * @return cardinalità della funzione parziale degli esiti
     * @post This è invariata.
     */
    public int passedExamCount() { return passedExams.size(); }

    /**
     * Controlla l'invariante della mappa concreta degli esiti.
     *
     * @pre studentId è stato assegnato e passedExams è inizializzata.
     * @post Se il RI vale termina senza modificare lo stato; con le asserzioni
     *       abilitate, se il RI non vale lancia {@link AssertionError}.
     */
    private void checkRep() {
        assert studentId != null && passedExams != null;
        for (Map.Entry<CourseCode, ExamRecord> entry : passedExams.entrySet()) {
            assert entry.getKey() != null;
            assert entry.getValue() != null;
            assert entry.getKey().equals(entry.getValue().courseCode());
        }
    }
}
