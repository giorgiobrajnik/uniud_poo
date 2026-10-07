package it.universita.esse3.confronti;

import java.util.Objects;

// Objects.equals gestisce i campi null; la nota non partecipa a equals e hashCode
public class Prenotazione {
    private final String studenteId;
    private final String appelloId;
    private String note;

    public Prenotazione(String studenteId, String appelloId, String note) {
        this.studenteId = studenteId;
        this.appelloId = appelloId;
        this.note = note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Prenotazione)) {
            return false;
        }
        Prenotazione that = (Prenotazione) other;
        return Objects.equals(studenteId, that.studenteId)
                && Objects.equals(appelloId, that.appelloId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studenteId, appelloId);
    }
}
