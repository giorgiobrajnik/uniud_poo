package it.uniud.tda.intranet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ValueObjectsTest {
    @Test
    void matricoleECodiciSonoValoriValidati() {
        assertEquals(new StudentId("123456"), new StudentId("123456"));
        assertEquals(new CourseCode("poo101"), new CourseCode("POO101"));
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() {
                new StudentId("12A");
            }
        });
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() {
                new CourseCode("101");
            }
        });
    }

    @Test
    void votoECfuProteggonoIPropriInvarianti() {
        assertEquals(30, Grade.withHonors().numericValue());
        assertTrue(Grade.withHonors().hasHonors());
        assertTrue(Grade.of(28).isAtLeast(Grade.of(24)));
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() {
                Grade.of(17);
            }
        });
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() {
                new CreditUnits(0);
            }
        });
    }
}
