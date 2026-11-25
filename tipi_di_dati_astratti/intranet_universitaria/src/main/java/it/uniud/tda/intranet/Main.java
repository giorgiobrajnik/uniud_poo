package it.uniud.tda.intranet;

import java.time.LocalDate;
import java.util.List;

/** Mostra un flusso completo dall'anagrafica alla media pesata. */
public final class Main {
    /**
     * Impedisce l'istanziazione della classe dimostrativa priva di stato.
     * @post È costruita un'istanza senza stato osservabile.
     */
    private Main() { }

    /**
     * Esegue iscrizioni e verbalizzazioni su un'intranet in memoria.
     * @param args non utilizzati
     * @post Stampa gli esiti del protocollo pubblico senza accedere alle rappresentazioni.
     */
    public static void main(String[] args) {
        UniversityIntranet intranet = new UniversityIntranet();
        StudentId adaId = new StudentId("123456");
        intranet.addStudent(new Student(adaId, "Ada Lovelace", "ada@uni.example"));

        Course programming = new Course(new CourseCode("POO101"),
                "Programmazione a oggetti", new CreditUnits(9), List.of());
        Course softwareDesign = new Course(new CourseCode("SWE201"),
                "Progettazione del software", new CreditUnits(6),
                List.of(new Prerequisite(programming.code(), Grade.of(24))));
        intranet.addCourse(programming);
        intranet.addCourse(softwareDesign);

        ExamSession programmingExam = new ExamSession(new ExamSessionId("POO-2027-01"),
                programming, LocalDate.of(2027, 1, 20),
                LocalDate.of(2027, 1, 1), LocalDate.of(2027, 1, 15), 30);
        intranet.scheduleExam(programmingExam);
        intranet.enroll(adaId, programmingExam.id(), LocalDate.of(2027, 1, 10));
        intranet.recordGrade(adaId, programmingExam.id(), Grade.of(28),
                LocalDate.of(2027, 1, 21));

        ExamSession designExam = new ExamSession(new ExamSessionId("SWE-2027-02"),
                softwareDesign, LocalDate.of(2027, 2, 20),
                LocalDate.of(2027, 2, 1), LocalDate.of(2027, 2, 15), 20);
        intranet.scheduleExam(designExam);
        intranet.enroll(adaId, designExam.id(), LocalDate.of(2027, 2, 10));
        intranet.recordGrade(adaId, designExam.id(), Grade.withHonors(),
                LocalDate.of(2027, 2, 21));

        Career career = intranet.findCareer(adaId).orElseThrow();
        System.out.println("Studente: " + intranet.findStudent(adaId).orElseThrow().name());
        System.out.println("Esami superati: " + career.passedExamCount());
        System.out.println("CFU acquisiti: " + career.earnedCredits());
        System.out.printf("Media pesata: %.2f%n", career.weightedAverage().orElseThrow());
        System.out.println("Ricerca corso assente: "
                + intranet.findCourse(new CourseCode("WEB999")).orElse(null));
    }
}
