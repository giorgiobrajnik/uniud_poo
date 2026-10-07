package it.universita.esse3.valori;

import java.util.Objects;

// Se l'uguaglianza usa solo una parte dei dati, equals e hashCode vanno scritti a mano
public record StudenteRecordPerMatricola(String matricola, String nome) {
    @Override
    public boolean equals(Object other) {
        return other instanceof StudenteRecordPerMatricola that
                && matricola.equals(that.matricola);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(matricola);
    }
}
