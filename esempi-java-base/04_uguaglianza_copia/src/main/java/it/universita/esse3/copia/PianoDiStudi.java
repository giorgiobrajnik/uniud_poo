package it.universita.esse3.copia;

import java.util.ArrayList;
import java.util.List;

public final class PianoDiStudi {
    private final StudenteId studenteId;
    private final List<AttivitaPiano> attivita;

    public PianoDiStudi(StudenteId studenteId) {
        this.studenteId = studenteId;
        this.attivita = new ArrayList<>();
    }

    /**
     * Crea una copia indipendente del piano.
     * La lista delle attività e ciascuna AttivitaPiano sono copiate.
     * StudenteId è condiviso perché immutabile.
     */
    public PianoDiStudi(PianoDiStudi original) {
        this.studenteId = original.studenteId;
        this.attivita = new ArrayList<>();
        for (AttivitaPiano a : original.attivita) {
            this.attivita.add(new AttivitaPiano(a));
        }
    }

    // Il nome dichiara che la nuova istanza fotografa lo stato corrente
    public static PianoDiStudi snapshotOf(PianoDiStudi original) {
        return new PianoDiStudi(original);
    }

    public StudenteId studenteId() {
        return studenteId;
    }

    public void aggiungi(AttivitaPiano a) {
        attivita.add(a);
    }

    public int numeroAttivita() {
        return attivita.size();
    }

    public AttivitaPiano attivita(int indice) {
        return attivita.get(indice);
    }
}
