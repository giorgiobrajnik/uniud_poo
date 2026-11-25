package it.uniud.tda.intranet;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Missione: mantenere i registri dell'intranet e coordinare operazioni che
 * attraversano studenti, carriere, corsi e appelli.
 *
 * <p>Stato astratto: insiemi indicizzati di studenti, carriere, corsi e
 * appelli. Ogni studente ha esattamente una carriera; ogni appello riguarda
 * un corso registrato.</p>
 */
public final class UniversityIntranet {
    /*
     * RI: mappe non null, senza chiavi/valori null; chiavi coerenti con gli ID;
     * studenti e carriere hanno le stesse matricole; corsi prerequisiti e corsi
     * degli appelli sono registrati; nessuna mappa è esposta.
     * AF: ogni voce delle mappe è un'entità registrata e le operazioni
     * coordinate delegano le regole locali agli oggetti che le possiedono.
     */
    private final Map<StudentId, Student> students = new HashMap<>();
    private final Map<StudentId, Career> careers = new HashMap<>();
    private final Map<CourseCode, Course> courses = new HashMap<>();
    private final Map<ExamSessionId, ExamSession> sessions = new HashMap<>();

    /**
     * Crea una intranet vuota.
     * @post Non contiene studenti, corsi o appelli.
     */
    public UniversityIntranet() {
        checkRep();
    }

    /**
     * Registra uno studente e crea la sua carriera vuota.
     * @param student studente
     * @throws NullPointerException se student è null
     * @throws DuplicateEntityException se la matricola esiste già
     * @post Lo studente è reperibile e possiede una carriera vuota; il resto è invariato.
     */
    public void addStudent(Student student) {
        Objects.requireNonNull(student, "student");
        if (students.containsKey(student.id())) {
            throw new DuplicateEntityException("Matricola già registrata: " + student.id().value());
        }
        students.put(student.id(), student);
        careers.put(student.id(), new Career(student.id()));
        checkRep();
    }

    /**
     * Registra un corso i cui prerequisiti sono già noti.
     * @param course corso
     * @throws NullPointerException se course è null
     * @throws DuplicateEntityException se il codice esiste già
     * @throws EntityNotFoundException se un prerequisito non è registrato
     * @post Il corso è reperibile; ogni altra entità è invariata.
     */
    public void addCourse(Course course) {
        Objects.requireNonNull(course, "course");
        if (courses.containsKey(course.code())) {
            throw new DuplicateEntityException("Corso già registrato: " + course.code().value());
        }
        for (Prerequisite prerequisite : course.prerequisites()) {
            if (!courses.containsKey(prerequisite.courseCode())) {
                throw new EntityNotFoundException("Prerequisito non registrato: "
                        + prerequisite.courseCode().value());
            }
        }
        courses.put(course.code(), course);
        checkRep();
    }

    /**
     * Inserisce un appello per un corso già registrato.
     * @param session appello
     * @throws NullPointerException se session è null
     * @throws DuplicateEntityException se l'ID appello esiste già
     * @throws EntityNotFoundException se il corso non è registrato
     * @post L'appello è reperibile; il resto è invariato.
     */
    public void scheduleExam(ExamSession session) {
        Objects.requireNonNull(session, "session");
        if (sessions.containsKey(session.id())) {
            throw new DuplicateEntityException("Appello già registrato: " + session.id().value());
        }
        Course registered = courses.get(session.course().code());
        if (registered == null || !registered.equals(session.course())) {
            throw new EntityNotFoundException("Corso dell'appello non registrato nell'intranet");
        }
        sessions.put(session.id(), session);
        checkRep();
    }

    /**
     * Iscrive uno studente verificando anche carriera e prerequisiti.
     * @param studentId studente
     * @param sessionId appello
     * @param today data della richiesta
     * @throws EntityNotFoundException se studente o appello non esiste
     * @throws EnrollmentException se corso già superato, prerequisiti mancanti
     *         o una regola locale dell'appello non è soddisfatta
     * @throws NullPointerException se un argomento è null
     * @post Lo studente è iscritto all'appello; carriere e altre entità sono invariate.
     */
    public void enroll(StudentId studentId, ExamSessionId sessionId, LocalDate today) {
        Career career = requireCareer(studentId);
        ExamSession session = requireSession(sessionId);
        Objects.requireNonNull(today, "today");
        if (career.hasPassed(session.course().code())) {
            throw new EnrollmentException("Corso già superato");
        }
        List<Prerequisite> missing = session.course().missingPrerequisites(career);
        if (!missing.isEmpty()) {
            throw new EnrollmentException("Prerequisiti mancanti: " + missing);
        }
        session.book(studentId, today);
        checkRep();
    }

    /**
     * Cancella un'iscrizione delegando all'appello.
     * @param studentId studente
     * @param sessionId appello
     * @param today data della richiesta
     * @throws EntityNotFoundException se studente o appello non esiste
     * @throws EnrollmentException se la cancellazione non è consentita
     * @throws NullPointerException se un argomento è null
     * @post Lo studente non è più iscritto; ogni carriera è invariata.
     */
    public void cancelEnrollment(StudentId studentId, ExamSessionId sessionId, LocalDate today) {
        requireCareer(studentId);
        ExamSession session = requireSession(sessionId);
        session.cancel(studentId, Objects.requireNonNull(today, "today"));
        checkRep();
    }

    /**
     * Registra un voto per uno studente iscritto dopo la data d'esame.
     * @param studentId studente
     * @param sessionId appello
     * @param grade voto verbalizzabile
     * @param recordedOn data di verbalizzazione
     * @throws EntityNotFoundException se studente o appello non esiste
     * @throws GradeRegistrationException se lo studente non è iscritto, la data
     *         precede l'esame o il corso è già verbalizzato
     * @throws NullPointerException se un argomento è null
     * @post La carriera contiene il corso col voto e i CFU dell'appello; gli
     *       altri esiti e le iscrizioni sono invariati.
     */
    public void recordGrade(StudentId studentId, ExamSessionId sessionId,
                            Grade grade, LocalDate recordedOn) {
        Career career = requireCareer(studentId);
        ExamSession session = requireSession(sessionId);
        Objects.requireNonNull(grade, "grade");
        Objects.requireNonNull(recordedOn, "recordedOn");
        if (!session.isBooked(studentId)) {
            throw new GradeRegistrationException("Studente non iscritto all'appello");
        }
        if (recordedOn.isBefore(session.examDate())) {
            throw new GradeRegistrationException("Verbalizzazione precedente all'esame");
        }
        career.register(session.course(), grade, recordedOn);
        checkRep();
    }

    /**
     * Cerca uno studente.
     * @param id matricola
     * @return studente se registrato, altrimenti empty
     * @throws NullPointerException se id è null
     * @post Lo stato è invariato.
     */
    public Optional<Student> findStudent(StudentId id) {
        return Optional.ofNullable(students.get(Objects.requireNonNull(id, "id")));
    }

    /**
     * Cerca un corso.
     * @param code codice
     * @return corso se registrato, altrimenti empty
     * @throws NullPointerException se code è null
     * @post Lo stato è invariato.
     */
    public Optional<Course> findCourse(CourseCode code) {
        return Optional.ofNullable(courses.get(Objects.requireNonNull(code, "code")));
    }

    /**
     * Cerca un appello.
     * @param id identificatore
     * @return appello se registrato, altrimenti empty
     * @throws NullPointerException se id è null
     * @post Lo stato è invariato.
     */
    public Optional<ExamSession> findSession(ExamSessionId id) {
        return Optional.ofNullable(sessions.get(Objects.requireNonNull(id, "id")));
    }

    /**
     * Cerca la carriera di uno studente.
     * @param id matricola
     * @return carriera se lo studente è registrato, altrimenti empty
     * @throws NullPointerException se id è null
     * @post Lo stato è invariato; la carriera restituita resta governata dal proprio protocollo.
     */
    public Optional<Career> findCareer(StudentId id) {
        return Optional.ofNullable(careers.get(Objects.requireNonNull(id, "id")));
    }

    /**
     * Reperisce una carriera nella mappa concreta, rendendo l'assenza un errore.
     *
     * @param id chiave da cercare
     * @return il valore associato a id in careers
     * @throws NullPointerException se id è null
     * @throws EntityNotFoundException se careers non contiene id
     * @post Le mappe sono invariate e il risultato è careers.get(id).
     */
    private Career requireCareer(StudentId id) {
        Objects.requireNonNull(id, "studentId");
        Career career = careers.get(id);
        if (career == null) throw new EntityNotFoundException("Studente non registrato: " + id.value());
        return career;
    }

    /**
     * Reperisce un appello nella mappa concreta, rendendo l'assenza un errore.
     *
     * @param id chiave da cercare
     * @return il valore associato a id in sessions
     * @throws NullPointerException se id è null
     * @throws EntityNotFoundException se sessions non contiene id
     * @post Le mappe sono invariate e il risultato è sessions.get(id).
     */
    private ExamSession requireSession(ExamSessionId id) {
        Objects.requireNonNull(id, "sessionId");
        ExamSession session = sessions.get(id);
        if (session == null) throw new EntityNotFoundException("Appello non registrato: " + id.value());
        return session;
    }

    /**
     * Controlla l'invariante delle quattro mappe concrete.
     *
     * @pre I campi sono stati inizializzati.
     * @post Se il RI vale termina senza modificare le mappe; con le asserzioni
     *       abilitate, se il RI non vale lancia {@link AssertionError}.
     */
    private void checkRep() {
        assert students != null && careers != null && courses != null && sessions != null;
        assert students.keySet().equals(careers.keySet());

        for (Map.Entry<StudentId, Student> entry : students.entrySet()) {
            assert entry.getKey() != null;
            assert entry.getValue() != null;
            assert entry.getKey().equals(entry.getValue().id());
        }
        for (Map.Entry<StudentId, Career> entry : careers.entrySet()) {
            assert entry.getKey() != null;
            assert entry.getValue() != null;
            assert entry.getKey().equals(entry.getValue().studentId());
        }
        for (Map.Entry<CourseCode, Course> entry : courses.entrySet()) {
            assert entry.getKey() != null;
            assert entry.getValue() != null;
            assert entry.getKey().equals(entry.getValue().code());
        }
        for (Map.Entry<ExamSessionId, ExamSession> entry : sessions.entrySet()) {
            assert entry.getKey() != null;
            assert entry.getValue() != null;
            assert entry.getKey().equals(entry.getValue().id());
            Course registered = courses.get(entry.getValue().course().code());
            assert registered != null && registered.equals(entry.getValue().course());
        }
        for (Course course : courses.values()) {
            for (Prerequisite prerequisite : course.prerequisites()) {
                assert courses.containsKey(prerequisite.courseCode());
            }
        }
    }
}
