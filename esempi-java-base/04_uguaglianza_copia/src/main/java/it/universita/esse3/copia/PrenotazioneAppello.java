package it.universita.esse3.copia;

public final class PrenotazioneAppello {
    private final String matricola;
    private final AppelloEsame appello;

    public PrenotazioneAppello(String matricola, AppelloEsame appello) {
        this.matricola = matricola;
        this.appello = appello;
    }

    public String matricola() {
        return matricola;
    }

    public AppelloEsame appello() {
        return appello;
    }

    // Una seconda prenotazione per lo stesso appello: l'entità resta condivisa
    public PrenotazioneAppello copiaCondivisa() {
        return new PrenotazioneAppello(matricola, appello);
    }

    // CONTROESEMPIO: un nuovo AppelloEsame non segue più lo spostamento dell'appello vero
    public PrenotazioneAppello copiaProfondaCieca() {
        return new PrenotazioneAppello(matricola, new AppelloEsame(appello));
    }
}
