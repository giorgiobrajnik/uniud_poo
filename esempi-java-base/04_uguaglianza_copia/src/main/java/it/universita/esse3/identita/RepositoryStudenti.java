package it.universita.esse3.identita;

// Simula un database: ogni load restituisce una nuova istanza
public final class RepositoryStudenti {
    public Studente load(String matricola) {
        return new Studente(matricola, "Anna Rossi");
    }
}
