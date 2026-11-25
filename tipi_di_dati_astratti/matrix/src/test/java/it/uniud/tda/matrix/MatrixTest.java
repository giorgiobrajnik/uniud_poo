package it.uniud.tda.matrix;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    @Test
    void costruzioneFaCopiaProfonda() {
        int[][] source = {{1, 2}};
        Matrix matrix = Matrix.of(source);
        source[0][0] = 10;
        assertEquals(BigInteger.ONE, matrix.entry(0, 0));
        assertThrows(UnsupportedOperationException.class, new Executable() {
            @Override
            public void execute() {
                matrix.row(0).set(0, BigInteger.ZERO);
            }
        });
    }

    @Test
    void conservaDimensioniVuote() {
        Matrix empty = Matrix.zero(0, 3);
        assertEquals(0, empty.rows());
        assertEquals(3, empty.columns());
        assertEquals(Matrix.zero(3, 0), empty.transpose());
    }

    @Test
    void addizioneTraspostaEProdottoSonoCorretti() {
        Matrix a = Matrix.of(new int[][]{
                {1, 2},
                {3, 4}
        });
        assertEquals(Matrix.of(new int[][]{
                {2, 4},
                {6, 8}
        }), a.add(a));
        assertEquals(Matrix.of(new int[][]{
                {1, 3},
                {2, 4}
        }), a.transpose());
        assertEquals(a, a.multiply(Matrix.identity(2)));
    }

    @Test
    void optionalRappresentaOperazioniNonDefinite() {
        Matrix rectangular = Matrix.zero(2, 3);
        assertEquals(Optional.empty(), rectangular.tryEntry(4, 1));
        assertEquals(Optional.empty(), rectangular.trace());
        assertEquals(Optional.of(BigInteger.valueOf(2)), Matrix.identity(2).trace());
    }

    @Test
    void erroriDiDimensioneSonoEspliciti() {
        Matrix a = Matrix.zero(2, 3);
        Matrix b = Matrix.zero(4, 2);
        assertThrows(DimensionMismatchException.class, new Executable() {
            @Override
            public void execute() {
                a.add(b);
            }
        });
        assertThrows(DimensionMismatchException.class, new Executable() {
            @Override
            public void execute() {
                a.multiply(b);
            }
        });
        assertThrows(IndexOutOfBoundsException.class, new Executable() {
            @Override
            public void execute() {
                a.entry(-1, 0);
            }
        });
    }

    @Test
    void withEntryNonModificaLaMatriceOriginaria() {
        Matrix identity = Matrix.identity(2);
        Matrix changed = identity.withEntry(0, 1, 7);
        assertEquals(BigInteger.ZERO, identity.entry(0, 1));
        assertEquals(BigInteger.valueOf(7), changed.entry(0, 1));
    }

    @Test
    void converteGliIntSenzaLimitareIRisultatiA32Bit() {
        Matrix maximum = Matrix.of(new int[][]{{Integer.MAX_VALUE}});
        Matrix squared = maximum.multiply(maximum);

        assertEquals(BigInteger.valueOf(Integer.MAX_VALUE).pow(2), squared.entry(0, 0));
    }
}
