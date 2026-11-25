package it.uniud.tda.intranet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CareerTest {
    @Test
    void carrieraVuotaUsaSentinelle() {
        Career career = new Career(new StudentId("123456"));
        assertTrue(career.weightedAverage().isEmpty());
        assertTrue(career.resultFor(new CourseCode("POO101")).isEmpty());
        assertEquals(0, career.earnedCredits());
    }

    @Test
    void calcolaMediaPesataPerCfu() {
        Career career = new Career(new StudentId("123456"));
        Course first = new Course(new CourseCode("POO101"), "POO",
                new CreditUnits(6), List.of());
        Course second = new Course(new CourseCode("MAT101"), "Matematica",
                new CreditUnits(12), List.of());
        career.register(first, Grade.of(24), LocalDate.of(2027, 1, 1));
        career.register(second, Grade.of(30), LocalDate.of(2027, 2, 1));

        assertEquals(18, career.earnedCredits());
        assertEquals(28.0, career.weightedAverage().orElseThrow(), 0.0001);
        assertThrows(GradeRegistrationException.class, new Executable() {
            @Override
            public void execute() {
                career.register(first, Grade.of(30), LocalDate.of(2027, 3, 1));
            }
        });
    }
}
