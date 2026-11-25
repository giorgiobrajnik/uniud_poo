package it.uniud.tda.polynomial.immutabile;

import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Missione: rappresentare un polinomio univariato in Z[x] come valore
 * immutabile e offrirne le operazioni algebriche fondamentali.
 *
 * <p>Stato astratto: una funzione da esponenti naturali a coefficienti interi
 * con supporto finito. Il polinomio nullo è ammesso e ha grado -1.</p>
 *
 * <p>Il <strong>supporto</strong> di un polinomio è l'insieme degli esponenti
 * ai quali è associato un coefficiente diverso da zero. Per esempio, il
 * supporto di 3 − 2x + 5x² è l'insieme formato da 0, 1 e 2; il supporto di
 * 4x¹⁰⁰ contiene soltanto 100; il supporto del polinomio nullo è vuoto.
 * Richiedere che il supporto sia finito significa che un polinomio può avere
 * soltanto un numero finito di termini non nulli, anche se la funzione che lo
 * descrive assegna concettualmente un coefficiente a ogni esponente naturale.</p>
 *
 * <p>Esempi di stati astratti:</p>
 * <ul>
 *   <li>il polinomio nullo 0 associa il coefficiente 0 a ogni esponente;
 *       il suo supporto è vuoto e il suo grado è -1 (convenzionalmente);</li>
 *   <li>il polinomio costante 7 ha coefficiente 7 per x⁰ e coefficiente 0
 *       per x¹, x² e per tutte le potenze successive;</li>
 *   <li>il polinomio 3 − 2x + 5x² ha coefficienti 3, −2 e 5 rispettivamente
 *       per x⁰, x¹ e x²; tutti gli altri coefficienti sono 0 e il suo
 *       supporto è l'insieme degli esponenti 0, 1 e 2;</li>
 *   <li>il monomio 4x¹⁰⁰ ha coefficiente 4 soltanto per l'esponente 100:
 *       mostra che il grado può essere alto anche quando il supporto contiene
 *       un solo esponente.</li>
 * </ul>
 *
 *
 * <p>Invariante di classe: ogni esponente ha esattamente un coefficiente
 * intero e soltanto un numero finito di coefficienti è diverso da zero.</p>
 */
@EqualsAndHashCode
public final class Polynomial {
    /*
     * RI:
     * - coefficients != null e non contiene null;
     * - è vuota oppure l'ultimo elemento è diverso da zero;
     * - la lista è non modificabile e non è esposta ai client.
     * AF:
     * - rappresenta la somma coefficients[i] * x^i;
     * - la lista vuota rappresenta il polinomio nullo.
     */
    private final List<BigInteger> coefficients;

    /**
     * Costruisce un polinomio da una rappresentazione già normalizzata.
     * Una lista è normalizzata quando è vuota, nel caso del polinomio nullo,
     * oppure quando il suo ultimo coefficiente è diverso da zero. In questo
     * modo rappresentazioni equivalenti come [3, 2, 5],
     * [3, 2, 5, 0] e [3, 2, 5, 0, 0] vengono ricondotte
     * all'unica forma canonica [3, 2, 5].
     *
     * @param normalizedCoefficients coefficienti concreti in ordine di esponente
     * @throws NullPointerException se la lista o un suo elemento è null
     * @pre La lista è vuota oppure termina con un coefficiente non nullo.
     * @post coefficients è una copia non modificabile della lista e il
     *       RI vale; la lista ricevuta non è modificata né trattenuta.
     */
    private Polynomial(List<BigInteger> normalizedCoefficients) {
        this.coefficients = List.copyOf(normalizedCoefficients);
        checkRep();
    }

    /**
     * Crea il polinomio nullo.
     *
     * @return un polinomio con coefficiente zero per ogni esponente e grado -1
     * @post Il risultato rappresenta 0.
     */
    public static Polynomial zero() {
        return new Polynomial(List.of());
    }

    /**
     * Crea un polinomio dai coefficienti in ordine crescente di esponente.
     *
     * I coefficienti int ricevuti vengono convertiti in BigInteger prima di
     * essere memorizzati.
     *
     * @param coefficients il coefficiente in posizione i appartiene a x^i
     * @return il polinomio descritto dai coefficienti; gli zeri finali sono irrilevanti
     * @throws NullPointerException se l'array è null
     * @post Per ogni i nell'array, result.coefficient(i) è coefficients[i];
     *       oltre la sua lunghezza è zero. L'array non è modificato né trattenuto.
     */
    public static Polynomial of(int... coefficients) {
        Objects.requireNonNull(coefficients, "coefficients deve essere non null");
        List<BigInteger> converted = new ArrayList<>(coefficients.length);
        for (int coefficient : coefficients) {
            converted.add(BigInteger.valueOf(coefficient));
        }
        return from(converted);
    }

    /**
     * Crea un monomio.
     *
     * @param coefficient coefficiente int del monomio, convertito in BigInteger
     * @param exponent esponente del monomio
     * @return coefficient * x^exponent, oppure zero se il coefficiente è zero
     * @throws IllegalArgumentException se exponent è negativo
     * @post Solo il coefficiente di grado exponent può essere diverso da zero.
     */
    public static Polynomial monomial(int coefficient, int exponent) {
        requireNonNegative(exponent);
        List<BigInteger> values = new ArrayList<>();
        for (int i = 0; i < exponent; i++) {
            values.add(BigInteger.ZERO);
        }
        values.add(BigInteger.valueOf(coefficient));
        return from(values);
    }

    /**
     * Osserva il coefficiente di un termine.
     *
     * @param exponent esponente da osservare
     * @return il coefficiente di x^exponent, zero se il termine è assente
     * @throws IllegalArgumentException se exponent è negativo
     * @post Il risultato è il coefficiente richiesto e questo polinomio è invariato.
     */
    public BigInteger coefficient(int exponent) {
        requireNonNegative(exponent);
        return coefficientOrZero(exponent);
    }

    /**
     * Osserva il grado.
     *
     * @return -1 per il polinomio nullo, altrimenti il massimo esponente con coefficiente non nullo
     * @post Questo polinomio è invariato.
     */
    public int degree() {
        return coefficients.size() - 1;
    }

    /**
     * Verifica se questo è il polinomio nullo.
     *
     * @return true se e solo se ogni coefficiente è zero
     * @post Questo polinomio è invariato.
     */
    public boolean isZero() {
        return coefficients.isEmpty();
    }

    /**
     * Produce una variante con un coefficiente sostituito.
     *
     * @param exponent esponente da aggiornare nel risultato
     * @param value nuovo coefficiente int, convertito in BigInteger
     * @return il polinomio aggiornato
     * @throws IllegalArgumentException se exponent è negativo
     * @post Il coefficiente exponent del risultato vale value; ogni altro
     *       coefficiente coincide con quello di this; this è invariato.
     */
    public Polynomial withCoefficient(int exponent, int value) {
        requireNonNegative(exponent);
        List<BigInteger> result = new ArrayList<>(coefficients);
        while (result.size() <= exponent) {
            result.add(BigInteger.ZERO);
        }
        result.set(exponent, BigInteger.valueOf(value));
        return from(result);
    }

    /**
     * Somma due polinomi.
     *
     * @param other secondo addendo
     * @return la somma matematica
     * @throws NullPointerException se other è null
     * @post Per ogni k, result.coefficient(k) = this.coefficient(k) +
     *       other.coefficient(k); entrambi gli addendi sono invariati.
     */
    public Polynomial add(Polynomial other) {
        Objects.requireNonNull(other, "other");
        int size = Math.max(coefficients.size(), other.coefficients.size());
        List<BigInteger> result = new ArrayList<>(size);
        for (int exponent = 0; exponent < size; exponent++) {
            result.add(coefficientOrZero(exponent).add(other.coefficientOrZero(exponent)));
        }
        return from(result);
    }

    /**
     * Sottrae un polinomio.
     *
     * @param other sottraendo
     * @return la differenza matematica
     * @throws NullPointerException se other è null
     * @post Per ogni k, result.coefficient(k) = this.coefficient(k) -
     *       other.coefficient(k); gli operandi sono invariati.
     */
    public Polynomial subtract(Polynomial other) {
        Objects.requireNonNull(other, "other");
        int size = Math.max(coefficients.size(), other.coefficients.size());
        List<BigInteger> result = new ArrayList<>(size);
        for (int exponent = 0; exponent < size; exponent++) {
            result.add(coefficientOrZero(exponent).subtract(other.coefficientOrZero(exponent)));
        }
        return from(result);
    }

    /**
     * Moltiplica due polinomi.
     *
     * @param other secondo fattore
     * @return il prodotto matematico
     * @throws NullPointerException se other è null
     * @post Il coefficiente k del risultato è la somma dei prodotti dei
     *       coefficienti i e j degli operandi per i+j=k; gli operandi sono invariati.
     */
    public Polynomial multiply(Polynomial other) {
        Objects.requireNonNull(other, "other");
        if (isZero() || other.isZero()) {
            return zero();
        }
        List<BigInteger> result = zeroes(degree() + other.degree() + 1);
        for (int i = 0; i <= degree(); i++) {
            for (int j = 0; j <= other.degree(); j++) {
                int exponent = i + j;
                result.set(exponent, result.get(exponent)
                        .add(coefficients.get(i).multiply(other.coefficients.get(j))));
            }
        }
        return from(result);
    }

    /**
     * Calcola la derivata formale.
     *
     * @return la derivata di questo polinomio
     * @post Per k >= 0, il coefficiente k del risultato è (k+1) volte il
     *       coefficiente k+1 di this; this è invariato.
     */
    public Polynomial derivative() {
        if (degree() <= 0) {
            return zero();
        }
        List<BigInteger> result = new ArrayList<>(degree());
        for (int exponent = 1; exponent <= degree(); exponent++) {
            result.add(coefficients.get(exponent).multiply(BigInteger.valueOf(exponent)));
        }
        return from(result);
    }

    /**
     * Valuta il polinomio con lo schema di Horner: partendo dal coefficiente
     * di grado massimo, moltiplica il risultato parziale per x e aggiunge il
     * coefficiente successivo, senza calcolare separatamente le potenze di x.
     * Per esempio, 3 + 2x + 5x² viene calcolato come (5x + 2)x + 3.
     *
     * @param x valore int della variabile, convertito in BigInteger
     * @return la somma coefficient(k) * x^k per ogni k non negativo
     * @post Il risultato è il valore matematico in x e this è invariato.
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
     * Restituisce una notazione matematica leggibile.
     *
     * @return una rappresentazione testuale indipendente dalla struttura concreta
     * @post This è invariato.
     */
    @Override
    public String toString() {
        if (isZero()) {
            return "0";
        }
        StringBuilder text = new StringBuilder();
        for (int exponent = degree(); exponent >= 0; exponent--) {
            appendTerm(text, coefficients.get(exponent), exponent);
        }
        return text.toString();
    }

    /**
     * Normalizza una sequenza concreta di coefficienti e costruisce il valore.
     *
     * @param values lista non normalizzata
     * @return un polinomio privo di zeri finali nella rappresentazione
     * @throws NullPointerException se values o un suo elemento è null
     * @post Il risultato rappresenta gli stessi coefficienti di values e
     *       soddisfa il RI; values è invariata e non è trattenuta.
     */
    private static Polynomial from(List<BigInteger> values) {
        int size = values.size();
        while (size > 0 && values.get(size - 1).signum() == 0) {
            size--;
        }
        return new Polynomial(values.subList(0, size));
    }

    /**
     * Legge direttamente la rappresentazione, estendendola idealmente con zeri.
     *
     * @param exponent indice concreto da leggere
     * @return coefficients[exponent] se presente, zero altrimenti
     * @throws IndexOutOfBoundsException se exponent è negativo
     * @post La rappresentazione è invariata.
     */
    private BigInteger coefficientOrZero(int exponent) {
        return exponent < coefficients.size() ? coefficients.get(exponent) : BigInteger.ZERO;
    }

    /**
     * Alloca una lista concreta di zeri.
     *
     * @param size numero di celle
     * @return una nuova lista modificabile di lunghezza size, piena di zeri
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
     * Accoda alla rappresentazione testuale un termine concreto non nullo.
     *
     * @param text accumulatore del testo già prodotto
     * @param coefficient coefficiente del termine
     * @param exponent esponente del termine
     * @throws NullPointerException se text o coefficient è null
     * @pre exponent >= 0; text è vuoto oppure contiene già termini di
     *      esponente maggiore.
     * @post Se coefficient è zero text è invariato; altrimenti contiene in
     *       coda il termine, col segno e i separatori appropriati.
     */
    private static void appendTerm(StringBuilder text, BigInteger coefficient, int exponent) {
        if (coefficient.signum() == 0) {
            return;
        }
        if (!text.isEmpty()) {
            text.append(coefficient.signum() > 0 ? " + " : " - ");
        } else if (coefficient.signum() < 0) {
            text.append('-');
        }
        BigInteger absolute = coefficient.abs();
        if (exponent == 0 || !absolute.equals(BigInteger.ONE)) {
            text.append(absolute);
        }
        if (exponent > 0) {
            text.append('x');
            if (exponent > 1) {
                text.append('^').append(exponent);
            }
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
     * @pre Il campo coefficients è stato assegnato.
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
