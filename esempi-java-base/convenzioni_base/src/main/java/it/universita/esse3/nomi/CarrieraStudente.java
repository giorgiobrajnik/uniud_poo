package it.universita.esse3.nomi;

public class CarrieraStudente {
    private int creditiAcquisiti;
    private static final int CFU_LAUREA_TRIENNALE = 180;

    public CarrieraStudente(int creditiAcquisiti) {
        this.creditiAcquisiti = creditiAcquisiti;
    }

    public boolean haCompletatoCrediti() {
        return creditiAcquisiti >= CFU_LAUREA_TRIENNALE;
    }
}
