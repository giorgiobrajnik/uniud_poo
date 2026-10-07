package it.universita.esse3.confronti;

import java.util.Objects;

// CONTROESEMPIO: la nota amministrativa non dovrebbe far parte dell'uguaglianza
public class PrenotazioneConNote {
    private final String studenteId;
    private final String appelloId;
    private String note;

    public PrenotazioneConNote(String studenteId, String appelloId, String note) {
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
        if (!(other instanceof PrenotazioneConNote)) {
            return false;
        }
        PrenotazioneConNote that = (PrenotazioneConNote) other;
        return Objects.equals(studenteId, that.studenteId)
                && Objects.equals(appelloId, that.appelloId)
                && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studenteId, appelloId, note);
    }
}
