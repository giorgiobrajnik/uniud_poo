package it.universita.esse3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import it.universita.esse3.wrapper.Boxing;
import it.universita.esse3.wrapper.CostoAutoboxing;
import java.util.List;
import org.junit.jupiter.api.Test;

class WrapperTest {
    @Test
    void boxingAutoboxingUnboxing() {
        assertEquals(6, Boxing.boxingEsplicito(6));
        assertEquals(6, Boxing.autoboxing(6));
        assertEquals(30, Boxing.unboxing(30));
        assertEquals(List.of(30, 27), Boxing.votiConAutoboxing());
    }

    @Test
    void controesempioUnboxingDiNull() {
        assertThrows(NullPointerException.class, () -> Boxing.votoNumerico(null));
    }

    @Test
    void unboxingProtettoDaControlloSuNull() {
        assertEquals(0, Boxing.votoNumericoODefault(null, 0));
        assertEquals(28, Boxing.votoNumericoODefault(28, 0));
    }

    @Test
    void wrapperEPrimitivoDannoLoStessoRisultato() {
        assertEquals(CostoAutoboxing.sommaConPrimitivo(1_000), CostoAutoboxing.sommaConWrapper(1_000));
    }
}
