package it.universita.esse3.identita;

/**
 * Identità: {@code ==} su riferimenti chiede «è la stessa istanza?», su primitivi
 * confronta i valori. Due istanze con gli stessi dati restano distinte; la stessa
 * entità di dominio (stessa matricola) può essere rappresentata da istanze diverse.
 */
public class EsempioIdentita {
    public static void main(String[] args) {
        System.out.println("--- Riferimenti: alias e seconda new ---");
        Studente s1 = new Studente("123456", "Anna Rossi");
        Studente s2 = s1;
        Studente s3 = new Studente("123456", "Anna Rossi");
        System.out.println("s1 == s2: " + (s1 == s2));
        System.out.println("s1 == s3: " + (s1 == s3));

        System.out.println("--- Controesempio: equals predefinito = identità ---");
        System.out.println("s1.equals(s3): " + s1.equals(s3));

        System.out.println("--- Primitivi: == confronta i valori ---");
        int a = 30;
        int b = 30;
        System.out.println("a == b: " + (a == b));

        System.out.println("--- Stessa entità di dominio, istanze diverse ---");
        RepositoryStudenti repository = new RepositoryStudenti();
        Studente d1 = repository.load("123456");
        Studente d2 = repository.load("123456");
        System.out.println("d1 == d2: " + (d1 == d2));
        System.out.println("stessa matricola: " + d1.matricola().equals(d2.matricola()));
    }
}
