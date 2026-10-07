package it.universita.esse3.variabili;

// CONTROESEMPIO: il parametro nasconde il campo e il compilatore non segnala nulla
public class CampoNascosto {
    private int crediti;

    public CampoNascosto(int crediti) {
        crediti = crediti;
    }

    public int getCrediti() {
        return crediti;
    }
}
