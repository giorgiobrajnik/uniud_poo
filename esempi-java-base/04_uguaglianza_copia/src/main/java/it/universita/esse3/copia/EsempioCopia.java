package it.universita.esse3.copia;

import java.time.LocalDate;

/**
 * Aliasing, shallow copy, deep copy. Assegnare un riferimento non copia l'oggetto;
 * {@link Carriera} mostra copia superficiale, copia con nuova lista e copia profonda;
 * {@link CopieDiCollezioni} le API della libreria; {@link PianoDiStudi} un costruttore di
 * copia con contratto esplicito; {@link PrenotazioneAppello} mostra che una deep copy cieca
 * duplica anche entità che dovevano restare condivise; {@link CopiaGrafo} il caso di
 * condivisioni e cicli.
 */
public class EsempioCopia {
    public static void main(String[] args) {
        System.out.println("--- Assegnare un riferimento non è copiare ---");
        Carriera c1 = new Carriera("123456");
        Carriera c2 = c1;
        c2.registraEsame(new EsameSuperato("POO", 27));
        System.out.println("c1 == c2: " + (c1 == c2));
        System.out.println("esami visti da c1: " + c1.numeroEsami());

        System.out.println("--- Controesempio: copia superficiale ---");
        Carriera superficiale = c1.copiaSuperficiale();
        superficiale.registraEsame(new EsameSuperato("BASI-DATI", 28));
        System.out.println("c1 == superficiale: " + (c1 == superficiale));
        System.out.println("esami visti da c1: " + c1.numeroEsami());

        System.out.println("--- Controesempio: nuova lista, elementi condivisi ---");
        Carriera nuovaLista = c1.copiaConNuovaLista();
        nuovaLista.registraEsame(new EsameSuperato("ALGEBRA", 24));
        nuovaLista.esame(0).setVoto(30);
        System.out.println("esami visti da c1: " + c1.numeroEsami());
        System.out.println("voto di POO visto da c1: " + c1.esame(0).voto());

        System.out.println("--- Copia profonda ---");
        Carriera profonda = c1.copiaProfonda();
        profonda.esame(0).setVoto(18);
        profonda.registraEsame(new EsameSuperato("FISICA", 21));
        System.out.println("voto di POO visto da c1: " + c1.esame(0).voto());
        System.out.println("esami visti da c1: " + c1.numeroEsami());

        System.out.println("--- Collezioni e array ---");
        System.out.println("new ArrayList<>(a): voto originale dopo setVoto(30) = "
                + CopieDiCollezioni.votoOriginaleDopoModificaViaCopiaArrayList());
        System.out.println("List.copyOf: voto originale dopo setVoto(30) = "
                + CopieDiCollezioni.votoOriginaleDopoModificaViaCopyOf());
        System.out.println("List.copyOf non permette add: " + CopieDiCollezioni.copyOfNonPermetteAggiunte());
        int[] dimensioni = CopieDiCollezioni.vistaNonModificabileContraCopyOf();
        System.out.println("vista: " + dimensioni[0] + " elementi, copyOf: " + dimensioni[1]);
        System.out.println("clone di int[]: a[0] = " + CopieDiCollezioni.primoValoreOriginaleDopoCloneDiPrimitivi());
        System.out.println("clone di array di riferimenti: voto originale = "
                + CopieDiCollezioni.votoOriginaleDopoCloneDiRiferimenti());

        System.out.println("--- Costruttore di copia con contratto ---");
        PianoDiStudi piano = new PianoDiStudi(new StudenteId("123456"));
        piano.aggiungi(new AttivitaPiano("POO", 9));
        PianoDiStudi snapshot = PianoDiStudi.snapshotOf(piano);
        piano.attivita(0).setCrediti(6);
        System.out.println("crediti nel piano: " + piano.attivita(0).crediti()
                + ", nello snapshot: " + snapshot.attivita(0).crediti());
        System.out.println("piano.equals(snapshot): " + piano.equals(snapshot));

        System.out.println("--- Controesempio: deep copy cieca di un'entità ---");
        AppelloEsame appello = new AppelloEsame("INF-POO-3", LocalDate.of(2026, 6, 10));
        PrenotazioneAppello prenotazione = new PrenotazioneAppello("123456", appello);
        PrenotazioneAppello condivisa = prenotazione.copiaCondivisa();
        PrenotazioneAppello cieca = prenotazione.copiaProfondaCieca();
        appello.sposta(LocalDate.of(2026, 6, 11));
        System.out.println("copia condivisa: " + condivisa.appello().data());
        System.out.println("copia cieca: " + cieca.appello().data());

        System.out.println("--- Grafo con condivisioni e cicli ---");
        Nodo radice = new Nodo("radice");
        Nodo p1 = new Nodo("prenotazione 1");
        Nodo p2 = new Nodo("prenotazione 2");
        Nodo appelloCondiviso = new Nodo("appello");
        radice.collega(p1);
        radice.collega(p2);
        p1.collega(appelloCondiviso);
        p2.collega(appelloCondiviso);
        Nodo ingenua = CopiaGrafo.copiaIngenua(radice);
        System.out.println("copia ingenua conserva la condivisione: "
                + (ingenua.collegato(0).collegato(0) == ingenua.collegato(1).collegato(0)));
        Nodo conMappa = CopiaGrafo.copiaConMappa(radice);
        System.out.println("copia con mappa conserva la condivisione: "
                + (conMappa.collegato(0).collegato(0) == conMappa.collegato(1).collegato(0)));

        Nodo studente = new Nodo("studente");
        Nodo carriera = new Nodo("carriera");
        studente.collega(carriera);
        carriera.collega(studente);
        try {
            CopiaGrafo.copiaIngenua(studente);
        } catch (StackOverflowError e) {
            System.out.println("copia ingenua di un ciclo: StackOverflowError");
        }
        Nodo cicloCopiato = CopiaGrafo.copiaConMappa(studente);
        System.out.println("copia con mappa di un ciclo: ciclo preservato = "
                + (cicloCopiato.collegato(0).collegato(0) == cicloCopiato));
    }
}
