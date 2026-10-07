package it.universita.esse3.difensiva;

import java.util.ArrayList;
import java.util.List;

/**
 * Copia difensiva in ingresso e in uscita. {@link AppelloEsposto} conserva la lista del
 * chiamante ed espone quella interna, quindi un client aggira {@code prenota} e la sua
 * chiusura; {@link AppelloProtetto} copia in ingresso e in uscita. Se gli elementi sono
 * mutabili {@code List.copyOf} non basta ({@link LibrettoElementiMutabili}): si restituiscono
 * snapshot immutabili ({@link LibrettoConSnapshot}).
 */
public class EsempioDifensiva {
    public static void main(String[] args) {
        System.out.println("--- Controesempio: lista del chiamante condivisa ---");
        List<String> aule = new ArrayList<>();
        aule.add("Aula A");
        AppelloEsposto esposto = new AppelloEsposto(aule);
        aule.clear();
        System.out.println("aule dell'appello: " + esposto.numeroAule());

        System.out.println("--- Controesempio: getter che espone la lista ---");
        esposto.chiudiPrenotazioni();
        esposto.getIscritti().add("999999");
        esposto.getIscritti().add("999999");
        System.out.println("iscritti ad appello chiuso: " + esposto.getIscritti());

        System.out.println("--- Copia in ingresso e in uscita ---");
        aule = new ArrayList<>();
        aule.add("Aula A");
        AppelloProtetto protetto = new AppelloProtetto(aule);
        aule.clear();
        System.out.println("aule dell'appello: " + protetto.numeroAule());
        protetto.prenota("123456");
        try {
            protetto.iscritti().add("999999");
        } catch (UnsupportedOperationException e) {
            System.out.println("la copia restituita non è modificabile");
        }
        protetto.chiudiPrenotazioni();
        try {
            protetto.prenota("654321");
        } catch (IllegalStateException e) {
            System.out.println("prenota rispetta l'invariante: " + e.getMessage());
        }

        System.out.println("--- Elementi immutabili: basta copiare la lista ---");
        LibrettoImmutabile immutabile = new LibrettoImmutabile("123456",
                List.of(new EsameRegistrato("POO", 27)));
        System.out.println("esami: " + immutabile.esami());

        System.out.println("--- Controesempio: elementi mutabili ---");
        LibrettoElementiMutabili mutabile = new LibrettoElementiMutabili("123456",
                List.of(new EsameModificabile("POO", 27)));
        mutabile.esami().get(0).setVoto(18);
        System.out.println("voto dopo la modifica del client: " + mutabile.votoDi(0));

        System.out.println("--- Snapshot immutabili ---");
        LibrettoConSnapshot conSnapshot = new LibrettoConSnapshot("123456");
        conSnapshot.registra("POO", 27);
        List<EsameRegistrato> snapshot = conSnapshot.esami();
        conSnapshot.correggiVoto(0, 30);
        System.out.println("snapshot precedente: " + snapshot);
        System.out.println("stato attuale: " + conSnapshot.esami());
    }
}
