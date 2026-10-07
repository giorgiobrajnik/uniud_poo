# Tipi, conversioni, wrapper

Esempi (✅) e controesempi (❌) su: tipi primitivi, virgola mobile, definite assignment, conversioni, stringhe, wrapper.
Ogni cartella sotto `src/main/java/it/universita/esse3/` è un argomento e ha un `main` eseguibile.

## Indice

### `primitivi/` - tipi primitivi (dispensa §6-7.1, 7.5, 7.6)

Esecuzione: [EsempioPrimitivi](src/main/java/it/universita/esse3/primitivi/EsempioPrimitivi.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | [TipiPrimitivi](src/main/java/it/universita/esse3/primitivi/TipiPrimitivi.java) | intervalli, suffissi `L`/`F`, separatore `_`, tipo dei letterali |
| ❌ | [TipiPrimitivi](src/main/java/it/universita/esse3/primitivi/TipiPrimitivi.java) | un `char` è un'unità UTF-16: un simbolo Unicode può valerne due |
| ❌ | [BooleanEInteri](src/test/resources/non-compilabili/BooleanEInteri.java.txt) | `boolean b = 1;` e `int x = true;` non compilano |

### `overflow/` - overflow degli interi (§7.2)

Esecuzione: [EsempioOverflow](src/main/java/it/universita/esse3/overflow/EsempioOverflow.java)

| | File | Cosa mostra |
|---|---|---|
| ❌ | [Overflow](src/main/java/it/universita/esse3/overflow/Overflow.java) | `Integer.MAX_VALUE + 1` vale `Integer.MIN_VALUE`, senza eccezione |
| ✅ | [Overflow](src/main/java/it/universita/esse3/overflow/Overflow.java) | `Math.addExact` e `Math.multiplyExact` lanciano `ArithmeticException`; un `long` rappresenta il risultato |

### `virgolamobile/` - `float`, `double`, denaro (§7.3-7.4)

Esecuzione: [EsempioVirgolaMobile](src/main/java/it/universita/esse3/virgolamobile/EsempioVirgolaMobile.java)

| | File | Cosa mostra |
|---|---|---|
| ❌ | [Approssimazione](src/main/java/it/universita/esse3/virgolamobile/Approssimazione.java) | `0.1 + 0.2` non è `0.3`; confronto con tolleranza |
| ✅ | [Ieee754](src/main/java/it/universita/esse3/virgolamobile/Ieee754.java) | segno, esponente e mantissa di un `double` (esempio 5.5) |
| ✅ | [ValoriSpeciali](src/main/java/it/universita/esse3/virgolamobile/ValoriSpeciali.java) | `NaN`, `Infinity`; la divisione intera per zero lancia un'eccezione |
| ❌ | [Denaro](src/main/java/it/universita/esse3/virgolamobile/Denaro.java) | 10 × 0.1 con `double`; `new BigDecimal(0.1)` |
| ✅ | [Denaro](src/main/java/it/universita/esse3/virgolamobile/Denaro.java) | centesimi in `long`; `BigDecimal` da `String` |

### `assegnamento/` - variabili locali e definite assignment (§8)

Esecuzione: [EsempioAssegnamento](src/main/java/it/universita/esse3/assegnamento/EsempioAssegnamento.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | [VotoDaEsito](src/main/java/it/universita/esse3/assegnamento/VotoDaEsito.java) | entrambi i rami assegnano la variabile |
| ✅ | [ValoriDiDefault](src/main/java/it/universita/esse3/assegnamento/ValoriDiDefault.java) | i campi, a differenza delle locali, hanno un valore di default |
| ❌ | [LetturaSenzaAssegnamento](src/test/resources/non-compilabili/LetturaSenzaAssegnamento.java.txt) | lettura di una variabile mai assegnata |
| ❌ | [UnSoloRamoAssegna](src/test/resources/non-compilabili/UnSoloRamoAssegna.java.txt) | solo il ramo `if` assegna |

### `conversioni/` - widening, narrowing, promozioni, divisione (§9)

Esecuzione: [EsempioConversioni](src/main/java/it/universita/esse3/conversioni/EsempioConversioni.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | [Widening](src/main/java/it/universita/esse3/conversioni/Widening.java) | `int` → `long`, `char` → `int` |
| ❌ | [Widening](src/main/java/it/universita/esse3/conversioni/Widening.java) | `long` → `double` ammesso ma non sempre esatto |
| ❌ | [Narrowing](src/main/java/it/universita/esse3/conversioni/Narrowing.java) | `(byte) 130`, troncamento verso zero, `(int)` su un `long` |
| ✅ | [PromozioniEDivisioni](src/main/java/it/universita/esse3/conversioni/PromozioniEDivisioni.java) | `byte + byte` è `int`; divisione in virgola mobile con cast |
| ❌ | [PromozioniEDivisioni](src/main/java/it/universita/esse3/conversioni/PromozioniEDivisioni.java) | `2 / 3` tra `int` vale `0` |
| ❌ | [ByteDaSomma](src/test/resources/non-compilabili/ByteDaSomma.java.txt) | `byte c = a + b;` non compila |
| ❌ | [IntDaDouble](src/test/resources/non-compilabili/IntDaDouble.java.txt) | `int` da `double` senza cast non compila |

### `stringhe/` - parsing e conversione a testo (§10)

Esecuzione: [EsempioStringhe](src/main/java/it/universita/esse3/stringhe/EsempioStringhe.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | [ConversioniStringhe](src/main/java/it/universita/esse3/stringhe/ConversioniStringhe.java) | `parseInt`, `parseLong`, `parseDouble`, `toString`, `valueOf` |
| ❌ | [ConversioniStringhe](src/main/java/it/universita/esse3/stringhe/ConversioniStringhe.java) | `parseInt("trenta")` lancia `NumberFormatException`; `parseBoolean("vero")` vale `false` senza errori |

### `wrapper/` - boxing e unboxing (§11)

Esecuzione: [EsempioWrapper](src/main/java/it/universita/esse3/wrapper/EsempioWrapper.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | [Boxing](src/main/java/it/universita/esse3/wrapper/Boxing.java) | boxing, autoboxing, unboxing, `List<Integer>` |
| ❌ | [Boxing](src/main/java/it/universita/esse3/wrapper/Boxing.java) | unboxing di `null`: `NullPointerException` |
| ❌ | [CostoAutoboxing](src/main/java/it/universita/esse3/wrapper/CostoAutoboxing.java) | somma con `Long` contro `long` |

## Controesempi che non compilano

I file in [src/test/resources/non-compilabili](src/test/resources/non-compilabili) hanno estensione `.java.txt`, così non rompono la build.
[CompilatoreDiControesempi](src/test/java/it/universita/esse3/CompilatoreDiControesempi.java) li compila durante i test e verifica il codice d'errore del compilatore.

## Test

| Test | Argomento |
|---|---|
| [PrimitiviTest](src/test/java/it/universita/esse3/PrimitiviTest.java) | `primitivi` |
| [OverflowTest](src/test/java/it/universita/esse3/OverflowTest.java) | `overflow` |
| [VirgolaMobileTest](src/test/java/it/universita/esse3/VirgolaMobileTest.java) | `virgolamobile` |
| [AssegnamentoTest](src/test/java/it/universita/esse3/AssegnamentoTest.java) | `assegnamento` |
| [ConversioniTest](src/test/java/it/universita/esse3/ConversioniTest.java) | `conversioni` |
| [StringheTest](src/test/java/it/universita/esse3/StringheTest.java) | `stringhe` |
| [WrapperTest](src/test/java/it/universita/esse3/WrapperTest.java) | `wrapper` |

## Comandi

```bash
mvn test
mvn compile
java -cp target/classes it.universita.esse3.conversioni.EsempioConversioni
```
