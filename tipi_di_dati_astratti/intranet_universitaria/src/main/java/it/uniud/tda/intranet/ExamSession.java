package it.uniud.tda.intranet;

import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Missione: rappresentare un appello e governarne le iscrizioni secondo
 * finestra temporale, assenza di duplicati e capienza.
 *
 * <p>Stato astratto: identificatore, corso, data d'esame, intervallo di
 * iscrizione, capienza e insieme degli studenti iscritti.</p>
 */
@EqualsAndHashCode(of = "id")
public final class ExamSession {
    /*
     * RI: campi non null; capacity >= 0; opens <= closes <= examDate;
     * bookedStudents non contiene null, non è esposto e size <= capacity.
     * AF: i campi scalari descrivono l'appello e il set è l'insieme astratto
     * delle matricole iscritte.
     */
    private final ExamSessionId id;
    private final Course course;
    private final LocalDate examDate;
    private final LocalDate bookingOpens;
    private final LocalDate bookingCloses;
    private final int capacity;
    private final Set<StudentId> bookedStudents = new HashSet<>();

    /**
     * Crea un appello senza iscritti.
     * @param id identificatore
     * @param course insegnamento
     * @param examDate data d'esame
     * @param bookingOpens primo giorno prenotabile
     * @param bookingCloses ultimo giorno prenotabile
     * @param capacity posti disponibili iniziali
     * @throws NullPointerException se un riferimento è null
     * @throws IllegalArgumentException se le date non sono ordinate o capacity è negativa
     * @post Lo stato coincide con gli argomenti e bookedCount() == 0.
     */
    public ExamSession(ExamSessionId id, Course course, LocalDate examDate,
                       LocalDate bookingOpens, LocalDate bookingCloses, int capacity) {
        this.id = Objects.requireNonNull(id, "id");
        this.course = Objects.requireNonNull(course, "course");
        this.examDate = Objects.requireNonNull(examDate, "examDate");
        this.bookingOpens = Objects.requireNonNull(bookingOpens, "bookingOpens");
        this.bookingCloses = Objects.requireNonNull(bookingCloses, "bookingCloses");
        if (capacity < 0) throw new IllegalArgumentException("Capienza negativa: " + capacity);
        if (bookingOpens.isAfter(bookingCloses) || bookingCloses.isAfter(examDate)) {
            throw new IllegalArgumentException("Date dell'appello non ordinate");
        }
        this.capacity = capacity;
        checkRep();
    }

    /** @return l'identificatore; @post This è invariato. */
    public ExamSessionId id() { return id; }

    /** @return il corso; @post This è invariato. */
    public Course course() { return course; }

    /** @return la data d'esame; @post This è invariato. */
    public LocalDate examDate() { return examDate; }

    /**
     * Verifica l'apertura in una data.
     * @param date data da verificare
     * @return true se bookingOpens <= date <= bookingCloses
     * @throws NullPointerException se date è null
     * @post This è invariato.
     */
    public boolean isOpenOn(LocalDate date) {
        Objects.requireNonNull(date, "date");
        return !date.isBefore(bookingOpens) && !date.isAfter(bookingCloses);
    }

    /**
     * Verifica l'iscrizione di uno studente.
     * @param studentId matricola
     * @return true se e solo se la matricola appartiene agli iscritti
     * @throws NullPointerException se studentId è null
     * @post This è invariato.
     */
    public boolean isBooked(StudentId studentId) {
        return bookedStudents.contains(Objects.requireNonNull(studentId, "studentId"));
    }

    /** @return numero degli iscritti; @post This è invariato. */
    public int bookedCount() { return bookedStudents.size(); }

    /** @return capacity - bookedCount(); @post This è invariato. */
    public int availablePlaces() { return capacity - bookedStudents.size(); }

    /**
     * Iscrive uno studente rispettando le regole locali dell'appello.
     * @param studentId matricola
     * @param today data della richiesta
     * @throws NullPointerException se un argomento è null
     * @throws EnrollmentException se appello chiuso, pieno o studente già iscritto
     * @post Lo studente è iscritto; bookedCount() = old(bookedCount()) + 1;
     *       ogni altra iscrizione e ogni dato dell'appello sono invariati.
     */
    public void book(StudentId studentId, LocalDate today) {
        Objects.requireNonNull(studentId, "studentId");
        Objects.requireNonNull(today, "today");
        if (!isOpenOn(today)) throw new EnrollmentException("Iscrizioni chiuse per " + id.value());
        if (isBooked(studentId)) throw new EnrollmentException("Studente già iscritto");
        if (availablePlaces() == 0) throw new EnrollmentException("Appello completo");
        bookedStudents.add(studentId);
        checkRep();
    }

    /**
     * Cancella un'iscrizione entro la chiusura delle prenotazioni.
     * @param studentId matricola
     * @param today data della richiesta
     * @throws NullPointerException se un argomento è null
     * @throws EnrollmentException se la chiusura è trascorsa o lo studente non è iscritto
     * @post Lo studente non è iscritto; bookedCount() = old(bookedCount()) - 1;
     *       ogni altra iscrizione è invariata.
     */
    public void cancel(StudentId studentId, LocalDate today) {
        Objects.requireNonNull(studentId, "studentId");
        Objects.requireNonNull(today, "today");
        if (today.isAfter(bookingCloses)) throw new EnrollmentException("Cancellazione fuori termine");
        if (!bookedStudents.remove(studentId)) throw new EnrollmentException("Studente non iscritto");
        checkRep();
    }

    /**
     * Controlla l'invariante della rappresentazione concreta dell'appello.
     *
     * @pre Tutti i campi final sono stati assegnati e bookedStudents è stato
     *      inizializzato.
     * @post Se il RI vale termina senza modificare i campi; con le asserzioni
     *       abilitate, se il RI non vale lancia {@link AssertionError}.
     */
    private void checkRep() {
        assert id != null && course != null && examDate != null;
        assert bookingOpens != null && bookingCloses != null;
        assert capacity >= 0;
        assert !bookingOpens.isAfter(bookingCloses) && !bookingCloses.isAfter(examDate);
        assert bookedStudents != null;
        for (StudentId studentId : bookedStudents) {
            assert studentId != null;
        }
        assert bookedStudents.size() <= capacity;
    }
}
