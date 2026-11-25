# Matrix

`Matrix` è un TDA immutabile per matrici finite a coefficienti interi arbitrari (`BigInteger`). Gli input numerici pubblici sono `int` e vengono convertiti internamente in `BigInteger`. Offre creazione di matrici nulle e identità, osservazione di celle e righe, sostituzione immutabile, somma, prodotto per scalare, prodotto matriciale, trasposizione e traccia.

## Stato astratto, AF e RI

Lo stato comprende `rows`, `columns` e un elemento per ogni coppia valida. Dimensioni nulle sono ammesse; `zero(0, 3)` e `zero(0, 5)` sono valori distinti.

- **AF:** le dimensioni concrete sono quelle astratte; `entries[i][j]` rappresenta l'elemento `(i,j)`.
- **RI:** dimensioni non negative, array esterno lungo `rows`, ogni riga non null lunga `columns`, celle non null, nessun array condiviso con il client.

Il costruttore logico `of` esegue una copia profonda della struttura e converte ogni cella in `BigInteger`. Il costruttore concreto ha accesso privato ed è raggiunto solo dopo validazione.

## Errori

L'esempio mostra due scelte intenzionali:

- `entry` e le operazioni algebriche definiscono esplicitamente anche i casi
  errati tramite eccezioni; le incompatibilità tra forme producono la custom
  `DimensionMismatchException`;
- `tryEntry` e `trace` usano `Optional` quando il client vuole interrogare senza considerare l'assenza un errore.

## Esecuzione

```bash
mvn -f ../pom.xml -pl matrix test
mvn -f ../pom.xml -pl matrix exec:java \
  -Dexec.mainClass=it.uniud.tda.matrix.Main
```
