package it.universita.esse3.variabili;

import java.util.ArrayList;
import java.util.List;

public class Scope {
    public static List<String> verificaCarriera(int creditiAcquisiti) {
        List<String> uscita = new ArrayList<>();
        int soglia = 12;

        if (creditiAcquisiti < soglia) {
            int mancanti = soglia - creditiAcquisiti;
            uscita.add("Mancano " + mancanti + " CFU");
        }

        for (int i = 0; i < 3; i++) {
            uscita.add("Tentativo " + i);
        }
        return uscita;
    }

    // Se il valore serve dopo il blocco, la variabile va dichiarata prima
    public static int mancantiDichiaratiPrimaDelBlocco(int creditiAcquisiti) {
        int soglia = 12;
        int mancanti = 0;

        if (creditiAcquisiti < soglia) {
            mancanti = soglia - creditiAcquisiti;
        }
        return mancanti;
    }

    public static int bloccoInternoVedeLeVariabiliEsterne(int creditiAcquisiti, boolean tasseRegolari) {
        int soglia = 12;

        if (creditiAcquisiti < soglia) {
            if (tasseRegolari) {
                return soglia;
            }
        }
        return 0;
    }

    // Blocchi separati possono riusare lo stesso nome
    public static int stessoNomeInBlocchiSeparati(boolean studenteAttivo, boolean tasseRegolari) {
        int somma = 0;

        if (studenteAttivo) {
            int esito = 1;
            somma += esito;
        }

        if (tasseRegolari) {
            int esito = 2;
            somma += esito;
        }
        return somma;
    }
}
