package it.universita.esse3.variabili;

public class SessioneUtente {
    private static int sessioniCreate = 0;
    private final String username;

    public SessioneUtente(String username) {
        this.username = username;
        sessioniCreate++;
    }

    public String getUsername() {
        return username;
    }

    public static int getSessioniCreate() {
        return sessioniCreate;
    }
}
