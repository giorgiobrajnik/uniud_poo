package it.universita.esse3.uguaglianza;

// Value object: final, immutabile, equals e hashCode sul solo dato che lo definisce
public final class AnnoAccademico {
    private final int inizio;

    public AnnoAccademico(int inizio) {
        this.inizio = inizio;
    }

    public int inizio() {
        return inizio;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AnnoAccademico)) {
            return false;
        }
        AnnoAccademico that = (AnnoAccademico) other;
        return this.inizio == that.inizio;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(inizio);
    }
}
