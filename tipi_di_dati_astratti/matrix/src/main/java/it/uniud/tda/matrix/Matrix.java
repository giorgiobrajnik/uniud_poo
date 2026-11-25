package it.uniud.tda.matrix;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Missione: rappresentare una matrice immutabile di interi di dimensione
 * finita e offrire le operazioni fondamentali dell'algebra matriciale.
 *
 * <p>Stato astratto: due dimensioni e un intero per ogni coppia di indici
 * valida. Sono ammesse anche matrici 0 x n e n x 0.</p>
 *
 * <p>Invariante di classe: rows e columns sono non negativi e ogni coppia
 * valida individua esattamente un elemento.</p>
 */
public final class Matrix {
    /*
     * RI: rows, columns >= 0; entries != null; entries.length == rows; ogni
     * riga è non null, lunga columns e senza celle null; nessun array è esposto.
     * AF: dimensioni astratte = rows/columns; elemento (i,j) = entries[i][j].
     */
    private final int rows;
    private final int columns;
    private final BigInteger[][] entries;

    /**
     * Acquisisce una rappresentazione concreta già valida.
     *
     * @param rows numero concreto di righe
     * @param columns numero concreto di colonne
     * @param entries array rettangolare posseduto dal chiamante interno
     * @pre rows e columns sono non negativi; entries ha forma rows x columns,
     *      non contiene null e non è condiviso con client esterni.
     * @post I campi coincidono con gli argomenti, entries è acquisito senza
     *       copia e il RI vale.
     */
    private Matrix(int rows, int columns, BigInteger[][] entries) {
        this.rows = rows;
        this.columns = columns;
        this.entries = entries;
    }

    /**
     * Crea una matrice nulla conservando entrambe le dimensioni, anche vuote.
     *
     * @param rows numero di righe
     * @param columns numero di colonne
     * @return matrice rows x columns con tutti gli elementi zero
     * @throws IllegalArgumentException se una dimensione è negativa
     * @post Le dimensioni sono quelle richieste e ogni entry vale zero.
     */
    public static Matrix zero(int rows, int columns) {
        requireDimensions(rows, columns);
        BigInteger[][] values = new BigInteger[rows][columns];
        for (BigInteger[] row : values) {
            Arrays.fill(row, BigInteger.ZERO);
        }
        return trusted(rows, columns, values);
    }

    /**
     * Crea la matrice identità di ordine size.
     *
     * @param size ordine della matrice
     * @return matrice quadrata con uno sulla diagonale e zero altrove
     * @throws IllegalArgumentException se size è negativo
     * @post result è size x size e result.entry(i,j) vale uno se i=j, zero altrimenti.
     */
    public static Matrix identity(int size) {
        Matrix zero = zero(size, size);
        BigInteger[][] values = zero.copyEntries();
        for (int i = 0; i < size; i++) {
            values[i][i] = BigInteger.ONE;
        }
        return trusted(size, size, values);
    }

    /**
     * Crea una matrice rettangolare copiando profondamente gli array e
     * convertendo ogni elemento int in BigInteger.
     *
     * @param values righe della matrice
     * @return matrice avente gli elementi ricevuti
     * @throws NullPointerException se l'array o una riga è null
     * @throws IllegalArgumentException se le righe hanno lunghezze diverse
     * @post result ha gli stessi elementi; future modifiche di values non lo cambiano.
     */
    public static Matrix of(int[][] values) {
        Objects.requireNonNull(values, "values");
        int columns = values.length == 0 ? 0
                : Objects.requireNonNull(values[0], "row 0").length;
        return of(values.length, columns, values);
    }

    /**
     * Crea una matrice specificando le dimensioni per conservare casi 0 x n.
     *
     * @param rows numero di righe
     * @param columns numero di colonne
     * @param values array rettangolare rows x columns di valori int
     * @return una copia immutabile della matrice descritta
     * @throws NullPointerException se l'array o una riga è null
     * @throws IllegalArgumentException se dimensioni o forma non coincidono
     * @post Result osserva gli elementi ricevuti al momento della chiamata e non trattiene gli array.
     */
    public static Matrix of(int rows, int columns, int[][] values) {
        requireDimensions(rows, columns);
        Objects.requireNonNull(values, "values");
        if (values.length != rows) {
            throw new IllegalArgumentException("Attese " + rows + " righe, trovate " + values.length);
        }
        BigInteger[][] copy = new BigInteger[rows][columns];
        for (int i = 0; i < rows; i++) {
            int[] sourceRow = Objects.requireNonNull(values[i], "row " + i);
            if (sourceRow.length != columns) {
                throw new IllegalArgumentException("Riga " + i + ": attese " + columns
                        + " colonne, trovate " + sourceRow.length);
            }
            for (int j = 0; j < columns; j++) {
                copy[i][j] = BigInteger.valueOf(sourceRow[j]);
            }
        }
        return trusted(rows, columns, copy);
    }

    /**
     * Osserva il numero di righe.
     *
     * @return il numero non negativo di righe
     * @post This è invariata.
     */
    public int rows() {
        return rows;
    }

    /**
     * Osserva il numero di colonne.
     *
     * @return il numero non negativo di colonne
     * @post This è invariata.
     */
    public int columns() {
        return columns;
    }

    /**
     * Osserva un elemento, segnalando come errore un indice non valido.
     *
     * @param row indice di riga
     * @param column indice di colonna
     * @return elemento astratto (row,column)
     * @throws IndexOutOfBoundsException se la coppia non è valida
     * @post Result è l'elemento richiesto e this è invariata.
     */
    public BigInteger entry(int row, int column) {
        requireValidIndex(row, column);
        return entries[row][column];
    }

    /**
     * Tenta di osservare un elemento senza completamento eccezionale.
     *
     * @param row indice di riga
     * @param column indice di colonna
     * @return l'elemento se gli indici sono validi, altrimenti Optional.empty()
     * @post This è invariata; il risultato è presente se e solo se la coppia è valida.
     */
    public Optional<BigInteger> tryEntry(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            return Optional.empty();
        }
        return Optional.of(entries[row][column]);
    }

    /**
     * Restituisce una fotografia immutabile di una riga.
     *
     * @param row indice di riga
     * @return gli elementi della riga nell'ordine delle colonne
     * @throws IndexOutOfBoundsException se row non è valido
     * @post Modificare il riferimento al risultato non può modificare this; this è invariata.
     */
    public List<BigInteger> row(int row) {
        if (row < 0 || row >= rows) {
            throw new IndexOutOfBoundsException("Riga non valida: " + row);
        }
        return List.copyOf(Arrays.asList(entries[row].clone()));
    }

    /**
     * Produce una matrice con una cella sostituita.
     *
     * @param row riga della cella
     * @param column colonna della cella
     * @param value nuovo valore int, convertito in BigInteger
     * @return matrice aggiornata
     * @throws IndexOutOfBoundsException se gli indici non sono validi
     * @post Dimensioni invariate; la cella richiesta vale value, ogni altra
     *       coincide con this; this è invariata.
     */
    public Matrix withEntry(int row, int column, int value) {
        requireValidIndex(row, column);
        BigInteger[][] result = copyEntries();
        result[row][column] = BigInteger.valueOf(value);
        return trusted(rows, columns, result);
    }

    /**
     * Somma matrici della stessa forma.
     *
     * @param other secondo addendo
     * @return somma elemento per elemento
     * @throws NullPointerException se other è null
     * @throws DimensionMismatchException se le forme differiscono
     * @post Ogni entry del risultato è la somma delle entry corrispondenti;
     *       gli operandi sono invariati.
     */
    public Matrix add(Matrix other) {
        Objects.requireNonNull(other, "other");
        if (rows != other.rows || columns != other.columns) {
            throw new DimensionMismatchException("Somma tra " + shape() + " e " + other.shape());
        }
        BigInteger[][] result = new BigInteger[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = entries[i][j].add(other.entries[i][j]);
            }
        }
        return trusted(rows, columns, result);
    }

    /**
     * Moltiplica ogni elemento per uno scalare.
     *
     * @param scalar intero moltiplicatore, convertito in BigInteger
     * @return matrice con stessa forma e valori scalati
     * @post result.entry(i,j) = this.entry(i,j) * scalar; this è invariata.
     */
    public Matrix scale(int scalar) {
        BigInteger convertedScalar = BigInteger.valueOf(scalar);
        BigInteger[][] result = new BigInteger[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = entries[i][j].multiply(convertedScalar);
            }
        }
        return trusted(rows, columns, result);
    }

    /**
     * Calcola il prodotto righe per colonne.
     *
     * @param other fattore destro
     * @return matrice rows x other.columns
     * @throws NullPointerException se other è null
     * @throws DimensionMismatchException se columns != other.rows
     * @post L'entry (i,j) è la somma per k di this(i,k)*other(k,j);
     *       gli operandi sono invariati.
     */
    public Matrix multiply(Matrix other) {
        Objects.requireNonNull(other, "other");
        if (columns != other.rows) {
            throw new DimensionMismatchException("Prodotto tra " + shape() + " e " + other.shape());
        }
        BigInteger[][] result = new BigInteger[rows][other.columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.columns; j++) {
                BigInteger sum = BigInteger.ZERO;
                for (int k = 0; k < columns; k++) {
                    sum = sum.add(entries[i][k].multiply(other.entries[k][j]));
                }
                result[i][j] = sum;
            }
        }
        return trusted(rows, other.columns, result);
    }

    /**
     * Produce la trasposta.
     *
     * @return matrice columns x rows
     * @post result.entry(i,j) = this.entry(j,i); this è invariata.
     */
    public Matrix transpose() {
        BigInteger[][] result = new BigInteger[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[j][i] = entries[i][j];
            }
        }
        return trusted(columns, rows, result);
    }

    /**
     * Calcola la traccia quando l'operazione è definita.
     *
     * @return somma della diagonale se la matrice è quadrata, altrimenti empty
     * @post This è invariata; il risultato è presente se e solo se rows == columns.
     */
    public Optional<BigInteger> trace() {
        if (rows != columns) {
            return Optional.empty();
        }
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < rows; i++) {
            sum = sum.add(entries[i][i]);
        }
        return Optional.of(sum);
    }

    /**
     * Confronta matrici per dimensioni ed elementi.
     *
     * @param other oggetto da confrontare
     * @return true se e solo se other è una matrice con lo stesso stato astratto
     * @post This e other sono invariati.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Matrix matrix)) return false;
        return rows == matrix.rows && columns == matrix.columns
                && Arrays.deepEquals(entries, matrix.entries);
    }

    /**
     * Calcola un hash coerente con l'uguaglianza astratta.
     *
     * @return hash dipendente da dimensioni ed elementi
     * @post This è invariata.
     */
    @Override
    public int hashCode() {
        return 31 * (31 * rows + columns) + Arrays.deepHashCode(entries);
    }

    /**
     * Produce una descrizione per righe.
     *
     * @return forma e righe della matrice
     * @post This è invariata.
     */
    @Override
    public String toString() {
        List<List<BigInteger>> rowsView = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            rowsView.add(row(i));
        }
        return "Matrix" + shape() + rowsView;
    }

    /**
     * Crea una matrice acquisendo un array concreto già validato.
     *
     * @param rows numero di righe
     * @param columns numero di colonne
     * @param entries rappresentazione da trasferire al risultato
     * @return una matrice che possiede entries
     * @pre rows e columns sono non negativi; entries ha forma rows x columns,
     *      non contiene null e nessun client esterno ne conserva un alias.
     * @post Il risultato soddisfa il RI, usa entries come rappresentazione e
     *       non modifica gli argomenti.
     */
    private static Matrix trusted(int rows, int columns, BigInteger[][] entries) {
        Matrix matrix = new Matrix(rows, columns, entries);
        matrix.checkRep();
        return matrix;
    }

    /**
     * Copia profondamente l'array della rappresentazione.
     *
     * @return un nuovo array rows x columns con le stesse celle
     * @post Il risultato non condivide array con entries; entries è invariato.
     */
    private BigInteger[][] copyEntries() {
        BigInteger[][] copy = new BigInteger[rows][columns];
        for (int i = 0; i < rows; i++) {
            copy[i] = entries[i].clone();
        }
        return copy;
    }

    /**
     * Verifica che una coppia di indici appartenga alla forma concreta.
     *
     * @param row indice di riga
     * @param column indice di colonna
     * @throws IndexOutOfBoundsException se la coppia non è in entries
     * @post Se termina normalmente, 0 <= row < rows e
     *       0 <= column < columns; lo stato è invariato.
     */
    private void requireValidIndex(int row, int column) {
        if (row < 0 || row >= rows || column < 0 || column >= columns) {
            throw new IndexOutOfBoundsException("Indice (" + row + "," + column
                    + ") fuori da " + shape());
        }
    }

    /**
     * Verifica la validità di due dimensioni concrete.
     *
     * @param rows numero di righe
     * @param columns numero di colonne
     * @throws IllegalArgumentException se una dimensione è negativa
     * @post Se termina normalmente, rows e columns sono non negativi.
     */
    private static void requireDimensions(int rows, int columns) {
        if (rows < 0 || columns < 0) {
            throw new IllegalArgumentException("Dimensioni negative: " + rows + "x" + columns);
        }
    }

    /**
     * Descrive le dimensioni memorizzate.
     *
     * @return la stringa rows + "x" + columns
     * @post Lo stato concreto è invariato.
     */
    private String shape() {
        return rows + "x" + columns;
    }

    /**
     * Controlla l'invariante della rappresentazione concreta.
     *
     * @pre I tre campi sono stati assegnati.
     * @post Se il RI vale termina senza modificare lo stato; con le asserzioni
     *       abilitate, se il RI non vale lancia {@link AssertionError}.
     */
    private void checkRep() {
        assert rows >= 0 && columns >= 0;
        assert entries != null && entries.length == rows;
        for (BigInteger[] row : entries) {
            assert row != null && row.length == columns;
            for (BigInteger entry : row) {
                assert entry != null;
            }
        }
    }
}
