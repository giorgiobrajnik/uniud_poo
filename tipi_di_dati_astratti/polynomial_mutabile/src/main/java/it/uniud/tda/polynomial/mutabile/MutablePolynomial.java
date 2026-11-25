package it.uniud.tda.polynomial.mutabile;

import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Missione: rappresentare un polinomio corrente in Z[x], consentendo di
 * sostituire coefficienti e accumulare risultati sulla stessa identità.
 *
 * <p>Stato astratto: una funzione da N a Z a supporto finito. L'oggetto è
 * mutabile: tutti i suoi alias osservano le modifiche.</p>
 *
 * <p>Invariante di classe: il supporto è finito; il polinomio nullo è ammesso
 * e ha grado -1.</p>
 */
@EqualsAndHashCode
public final class MutablePolynomial {
    /*
     * RI: coefficients non è null, non contiene null ed è vuota oppure termina
     * con un valore non zero; la lista è posseduta esclusivamente dall'oggetto.
     * AF: coefficients[i] è il coefficiente astratto di x^i; lista vuota = 0.
     */
    private final List<BigInteger> coefficients;

    /**
     * Costruisce un polinomio da coefficienti concreti già normalizzati.
     *
     * @param normalized lista dei coefficienti
     * @throws NullPointerException se normalized è null
     * @pre normalized non contiene null ed è vuota oppure termina con un
     *      valore diverso da zero.
     * @post coefficients è una copia modificabile posseduta esclusivamente
     *       dall'oggetto, contiene gli stessi valori e soddisfa il RI.
     */
    private MutablePolynomial(List<BigInteger> normalized) {
        this.coefficients = new ArrayList<>(normalized);
        checkRep();
    }

    /**
     * Crea il polinomio nullo.
     *
     * @return un nuovo polinomio mutabile uguale a zero
     * @post Il risultato ha grado -1 e nessun alias con altri polinomi.
     */
    public static MutablePolynomial zero() {
        return new MutablePolynomial(List.of());
    }

    /**
     * Crea un polinomio dai coefficienti in ordine crescente di esponente.
     *
     * I coefficienti int ricevuti vengono convertiti in BigInteger prima di
     * essere memorizzati.
     *
     * @param coefficients coefficienti di x^0, x^1, ...
     * @return un nuovo polinomio indipendente dall'array ricevuto
     * @throws NullPointerException se l'array è null
     * @post Il coefficiente i del risultato coincide con coefficients[i];
     *       l'array non è trattenuto né modificato.
     */
    public static MutablePolynomial of(int... coefficients) {
        Objects.requireNonNull(coefficients, "coefficients");
        List<BigInteger> copy = new ArrayList<>(coefficients.length);
        for (int coefficient : coefficients) {
            copy.add(BigInteger.valueOf(coefficient));
        }
        normalize(copy);
        return new MutablePolynomial(copy);
    }

    /**
     * Osserva un coefficiente.
     *
     * @param exponent esponente da osservare
     * @return il coefficiente di x^exponent, zero oltre il grado
     * @throws IllegalArgumentException se exponent è negativo
     * @post Lo stato è invariato.
     */
    public BigInteger coefficient(int exponent) {
        requireNonNegative(exponent);
        return coefficientOrZero(exponent);
    }

    /**
     * Osserva il grado corrente.
     *
     * @return -1 se il polinomio è nullo, altrimenti il massimo esponente non nullo
     * @post Lo stato è invariato.
     */
    public int degree() {
        return coefficients.size() - 1;
    }

    /**
     * Verifica se il valore corrente è zero.
     *
     * @return true se e solo se tutti i coefficienti sono zero
     * @post Lo stato è invariato.
     */
    public boolean isZero() {
        return coefficients.isEmpty();
    }

    /**
     * Sostituisce un coefficiente.
     *
     * @param exponent termine da modificare
     * @param value nuovo coefficiente int, convertito in BigInteger
     * @throws IllegalArgumentException se exponent è negativo
     * @post coefficient(exponent) = value; per ogni k diverso da exponent,
     *       coefficient(k) = old(coefficient(k)).
     */
    public void setCoefficient(int exponent, int value) {
        requireNonNegative(exponent);
        while (coefficients.size() <= exponent) {
            coefficients.add(BigInteger.ZERO);
        }
        coefficients.set(exponent, BigInteger.valueOf(value));
        normalize(coefficients);
        checkRep();
    }

    /**
     * Accumula la somma in questo oggetto.
     *
     * @param other secondo addendo
     * @throws NullPointerException se other è null
     * @post Per ogni k, coefficient(k) = old(coefficient(k)) +
     *       old(other.coefficient(k)); other è invariato salvo il caso
     *       this == other, nel quale ogni vecchio coefficiente raddoppia.
     */
    public void addInPlace(MutablePolynomial other) {
        Objects.requireNonNull(other, "other");
        List<BigInteger> left = List.copyOf(coefficients);
        List<BigInteger> right = List.copyOf(other.coefficients);
        int size = Math.max(left.size(), right.size());
        List<BigInteger> result = new ArrayList<>(size);
        for (int exponent = 0; exponent < size; exponent++) {
            result.add(atOrZero(left, exponent).add(atOrZero(right, exponent)));
        }
        replaceWith(result);
    }

    /**
     * Accumula il prodotto in questo oggetto.
     *
     * @param other secondo fattore
     * @throws NullPointerException se other è null
     * @post Il nuovo coefficiente k è la convoluzione dei coefficienti vecchi
     *       di this e other; other è invariato salvo this == other,
     *       caso nel quale il risultato è il quadrato del vecchio valore.
     */
    public void multiplyInPlace(MutablePolynomial other) {
        Objects.requireNonNull(other, "other");
        List<BigInteger> left = List.copyOf(coefficients);
        List<BigInteger> right = List.copyOf(other.coefficients);
        if (left.isEmpty() || right.isEmpty()) {
            replaceWith(List.of());
            return;
        }
        List<BigInteger> result = zeroes(left.size() + right.size() - 1);
        for (int i = 0; i < left.size(); i++) {
            for (int j = 0; j < right.size(); j++) {
                int exponent = i + j;
                result.set(exponent, result.get(exponent).add(left.get(i).multiply(right.get(j))));
            }
        }
        replaceWith(result);
    }

    /**
     * Azzera il polinomio.
     *
     * @post Il grado vale -1 e ogni coefficiente vale zero.
     */
    public void clear() {
        coefficients.clear();
        checkRep();
    }

    /**
     * Crea una copia mutabile indipendente.
     *
     * @return una nuova istanza con lo stesso valore corrente
     * @post Result è uguale a this al momento della chiamata; future modifiche
     *       dell'uno non cambiano l'altro; this è invariato.
     */
    public MutablePolynomial copy() {
        return new MutablePolynomial(coefficients);
    }

    /**
     * Valuta il valore corrente con lo schema di Horner.
     *
     * @param x valore int della variabile, convertito in BigInteger
     * @return la somma coefficient(k) * x^k
     * @post Lo stato è invariato.
     */
    public BigInteger evaluate(int x) {
        BigInteger convertedX = BigInteger.valueOf(x);
        BigInteger result = BigInteger.ZERO;
        for (int exponent = degree(); exponent >= 0; exponent--) {
            result = result.multiply(convertedX).add(coefficients.get(exponent));
        }
        return result;
    }

    /**
     * Restituisce una descrizione matematica del valore corrente.
     *
     * @return coefficienti in notazione densa, senza esporre la lista posseduta
     * @post Lo stato è invariato.
     */
    @Override
    public String toString() {
        return "MutablePolynomial" + coefficients;
    }

    /**
     * Sostituisce la rappresentazione corrente con una sequenza normalizzata.
     *
     * @param replacement nuovi coefficienti concreti
     * @throws NullPointerException se replacement è null
     * @pre replacement non contiene null.
     * @post coefficients rappresenta i valori di replacement senza zeri
     *       finali, il RI vale e replacement è invariata.
     */
    private void replaceWith(List<BigInteger> replacement) {
        List<BigInteger> normalized = new ArrayList<>(replacement);
        normalize(normalized);
        coefficients.clear();
        coefficients.addAll(normalized);
        checkRep();
    }

    /**
     * Legge la rappresentazione estendendola idealmente con zeri.
     *
     * @param exponent indice concreto
     * @return la cella exponent, oppure zero se è oltre la lista
     * @throws IndexOutOfBoundsException se exponent è negativo
     * @post coefficients è invariata.
     */
    private BigInteger coefficientOrZero(int exponent) {
        return atOrZero(coefficients, exponent);
    }

    /**
     * Legge una lista concreta estendendola idealmente con zeri.
     *
     * @param values lista da osservare
     * @param exponent indice da leggere
     * @return values[exponent] se presente, zero altrimenti
     * @throws NullPointerException se values è null
     * @throws IndexOutOfBoundsException se exponent è negativo
     * @pre values non contiene null.
     * @post values è invariata.
     */
    private static BigInteger atOrZero(List<BigInteger> values, int exponent) {
        return exponent < values.size() ? values.get(exponent) : BigInteger.ZERO;
    }

    /**
     * Alloca una lista concreta di zeri.
     *
     * @param size numero di celle
     * @return una nuova lista modificabile di lunghezza size
     * @throws IllegalArgumentException se size è negativo
     * @post Ogni cella del risultato è {@link BigInteger#ZERO}.
     */
    private static List<BigInteger> zeroes(int size) {
        List<BigInteger> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(BigInteger.ZERO);
        }
        return result;
    }

    /**
     * Rimuove gli zeri finali dalla rappresentazione concreta.
     *
     * @param values lista modificabile da normalizzare
     * @pre values è non null, modificabile e non contiene null.
     * @post values rappresenta la stessa sequenza idealmente estesa con zeri
     *       ed è vuota oppure termina con un valore diverso da zero.
     */
    private static void normalize(List<BigInteger> values) {
        while (!values.isEmpty() && values.getLast().signum() == 0) {
            values.removeLast();
        }
    }

    /**
     * Verifica che un esponente sia non negativo.
     *
     * @param exponent esponente da validare
     * @throws IllegalArgumentException se exponent è negativo
     * @post Se termina normalmente, exponent >= 0.
     */
    private static void requireNonNegative(int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Esponente negativo: " + exponent);
        }
    }

    /**
     * Controlla l'invariante della rappresentazione concreta.
     *
     * @pre coefficients è stato assegnato.
     * @post Se il RI vale termina senza modificare lo stato; con le asserzioni
     *       abilitate, se il RI non vale lancia {@link AssertionError}.
     */
    private void checkRep() {
        assert coefficients != null;
        for (BigInteger coefficient : coefficients) {
            assert coefficient != null;
        }
        assert coefficients.isEmpty() || coefficients.getLast().signum() != 0;
    }
}
