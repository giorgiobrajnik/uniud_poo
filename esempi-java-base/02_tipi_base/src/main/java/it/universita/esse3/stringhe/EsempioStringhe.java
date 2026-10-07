package it.universita.esse3.stringhe;

/**
 * Conversioni da e verso stringhe.
 * Il parsing interpreta un testo come valore di un tipo: {@code Integer.parseInt},
 * {@code Long.parseLong}, {@code Double.parseDouble}, {@code Boolean.parseBoolean}.
 * Controesempi: {@code parseInt("trenta")} segnala l'errore con una
 * {@link NumberFormatException}, mentre {@code parseBoolean("vero")} non segnala nulla e
 * vale {@code false}, perche' solo "true" (senza badare alle maiuscole) vale vero.
 * Nel verso opposto si usano {@code Integer.toString} e {@code String.valueOf}.
 */
public class EsempioStringhe {
    public static void main(String[] args) {
        System.out.println("--- Parsing ---");
        System.out.println("Integer.parseInt(\"12\")          = " + ConversioniStringhe.parseCfu("12"));
        System.out.println("Long.parseLong(\"9000000001\")    = " + ConversioniStringhe.parseId("9000000001"));
        System.out.println("Double.parseDouble(\"27.5\")      = " + ConversioniStringhe.parseMedia("27.5"));
        System.out.println("Boolean.parseBoolean(\"true\")    = " + ConversioniStringhe.parseAttivo("true"));

        System.out.println("--- Controesempi: parsing di testo non valido ---");
        try {
            ConversioniStringhe.parseCfu("trenta");
        } catch (NumberFormatException e) {
            System.out.println("parseInt(\"trenta\"): " + e);
        }
        System.out.println("parseBoolean(\"vero\") = " + ConversioniStringhe.parseAttivo("vero") + " (nessun errore)");

        System.out.println("--- Da numero a stringa ---");
        System.out.println("Integer.toString(12)      = \"" + ConversioniStringhe.cfuComeTesto(12) + "\"");
        System.out.println("String.valueOf(123456789L) = \"" + ConversioniStringhe.idComeTesto(123456789L) + "\"");
    }
}
