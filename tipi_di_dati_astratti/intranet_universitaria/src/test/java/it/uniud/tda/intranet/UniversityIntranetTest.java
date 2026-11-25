package it.uniud.tda.intranet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UniversityIntranetTest {
    private UniversityIntranet intranet;
    private StudentId studentId;
    private Course baseCourse;
    private Course advancedCourse;

    @BeforeEach
    void setUp() {
        intranet = new UniversityIntranet();
        studentId = new StudentId("123456");
        intranet.addStudent(new Student(studentId, "Ada", "ada@uni.example"));
        baseCourse = new Course(new CourseCode("POO101"), "POO",
                new CreditUnits(6), List.of());
        advancedCourse = new Course(new CourseCode("SWE201"), "Software Engineering",
                new CreditUnits(12),
                List.of(new Prerequisite(baseCourse.code(), Grade.of(24))));
        intranet.addCourse(baseCourse);
        intranet.addCourse(advancedCourse);
    }

    @Test
    void prerequisitoMancanteImpedisceIscrizioneSenzaEffettiParziali() {
        ExamSession advanced = session("ADV-1", advancedCourse,
                LocalDate.of(2027, 2, 20), 2);
        intranet.scheduleExam(advanced);

        assertThrows(EnrollmentException.class, new Executable() {
            @Override
            public void execute() {
                intranet.enroll(studentId, advanced.id(), LocalDate.of(2027, 2, 10));
            }
        });
        assertFalse(advanced.isBooked(studentId));
        assertEquals(2, advanced.availablePlaces());
    }

    @Test
    void flussoCompletoProduceCreditiEMediaPesata() {
        ExamSession base = session("BASE-1", baseCourse, LocalDate.of(2027, 1, 20), 2);
        intranet.scheduleExam(base);
        intranet.enroll(studentId, base.id(), LocalDate.of(2027, 1, 10));
        intranet.recordGrade(studentId, base.id(), Grade.of(28), LocalDate.of(2027, 1, 21));

        ExamSession advanced = session("ADV-1", advancedCourse, LocalDate.of(2027, 2, 20), 2);
        intranet.scheduleExam(advanced);
        intranet.enroll(studentId, advanced.id(), LocalDate.of(2027, 2, 10));
        intranet.recordGrade(studentId, advanced.id(), Grade.of(30), LocalDate.of(2027, 2, 21));

        Career career = intranet.findCareer(studentId).orElseThrow();
        assertEquals(18, career.earnedCredits());
        assertEquals(29.333333, career.weightedAverage().orElseThrow(), 0.0001);
        assertEquals(Grade.of(30), career.resultFor(advancedCourse.code()).orElseThrow().grade());
    }

    @Test
    void capienzaDuplicatiEDateProduconoEccezioniDiDominio() {
        StudentId secondId = new StudentId("654321");
        intranet.addStudent(new Student(secondId, "Grace", "grace@uni.example"));
        ExamSession session = session("BASE-1", baseCourse, LocalDate.of(2027, 1, 20), 1);
        intranet.scheduleExam(session);

        assertThrows(EnrollmentException.class, new Executable() {
            @Override
            public void execute() {
                intranet.enroll(studentId, session.id(), LocalDate.of(2026, 12, 31));
            }
        });
        intranet.enroll(studentId, session.id(), LocalDate.of(2027, 1, 10));
        assertThrows(EnrollmentException.class, new Executable() {
            @Override
            public void execute() {
                intranet.enroll(studentId, session.id(), LocalDate.of(2027, 1, 10));
            }
        });
        assertThrows(EnrollmentException.class, new Executable() {
            @Override
            public void execute() {
                intranet.enroll(secondId, session.id(), LocalDate.of(2027, 1, 10));
            }
        });
        assertEquals(1, session.bookedCount());
    }

    @Test
    void verbalizzazioneRichiedeIscrizioneEDataValida() {
        ExamSession session = session("BASE-1", baseCourse, LocalDate.of(2027, 1, 20), 2);
        intranet.scheduleExam(session);
        assertThrows(GradeRegistrationException.class, new Executable() {
            @Override
            public void execute() {
                intranet.recordGrade(studentId, session.id(), Grade.of(25),
                        LocalDate.of(2027, 1, 21));
            }
        });
        intranet.enroll(studentId, session.id(), LocalDate.of(2027, 1, 10));
        assertThrows(GradeRegistrationException.class, new Executable() {
            @Override
            public void execute() {
                intranet.recordGrade(studentId, session.id(), Grade.of(25),
                        LocalDate.of(2027, 1, 19));
            }
        });
        assertTrue(intranet.findCareer(studentId).orElseThrow().resultFor(baseCourse.code()).isEmpty());
    }

    @Test
    void registriOffronoOptionalEDifendonoUnicita() {
        assertTrue(intranet.findCourse(baseCourse.code()).isPresent());
        assertTrue(intranet.findCourse(new CourseCode("WEB999")).isEmpty());
        assertThrows(DuplicateEntityException.class, new Executable() {
            @Override
            public void execute() {
                intranet.addCourse(baseCourse);
            }
        });
        assertThrows(EntityNotFoundException.class, new Executable() {
            @Override
            public void execute() {
                intranet.enroll(studentId, new ExamSessionId("UNKNOWN"), LocalDate.now());
            }
        });
    }

    /**
     * Prepara la rappresentazione di un appello usata dai test.
     * @post Il risultato ha gli argomenti indicati, apre 19 giorni prima,
     *       chiude 5 giorni prima e non contiene iscritti.
     */
    private ExamSession session(String id, Course course, LocalDate examDate, int capacity) {
        return new ExamSession(new ExamSessionId(id), course, examDate,
                examDate.minusDays(19), examDate.minusDays(5), capacity);
    }
}
