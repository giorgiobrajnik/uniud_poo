# Polynomial immutabile

`Polynomial` rappresenta un valore di `Z[x]`. Il protocollo comprende creatori (`zero`, `of`, `monomial`), osservatori (`coefficient`, `degree`, `isZero`, `evaluate`) e produttori (`withCoefficient`, `add`, `subtract`, `multiply`, `derivative`). Non contiene modificatori.

## Stato astratto e invariante di classe

Lo stato è una funzione `p: N -> Z` a supporto finito. Il polinomio nullo è ammesso e ha grado `-1`. Ogni operazione algebrica restituisce un nuovo valore e lascia invariati gli operandi.

## Rappresentazione

I coefficienti sono conservati in una lista densa non modificabile, in ordine crescente di esponente.
Gli input numerici del protocollo pubblico sono `int` e vengono convertiti in
`BigInteger`; la rappresentazione e i risultati algebrici non sono quindi
limitati all'intervallo degli interi a 32 bit.

- **AF:** `[a0, ..., an]` rappresenta `a0 + ... + an*x^n`; la lista vuota rappresenta zero.
- **RI:** lista ed elementi non null; lista vuota oppure ultimo elemento non zero; nessun alias mutabile viene esposto.

L'annotazione Lombok `@EqualsAndHashCode` è sicura perché la rappresentazione è canonica e immutabile. Il `toString` è scritto a mano perché appartiene al linguaggio del dominio, non alla rappresentazione.

## Errori e casi limite

Un esponente negativo è un caso definito dal contratto e causa
`IllegalArgumentException`; un riferimento `null` causa
`NullPointerException`. Coefficienti oltre il grado valgono zero. Zeri finali
e somme che si annullano sono normalizzati.

## Esecuzione

```bash
mvn -f ../pom.xml -pl polynomial_immutabile test
mvn -f ../pom.xml -pl polynomial_immutabile exec:java \
  -Dexec.mainClass=it.uniud.tda.polynomial.immutabile.Main
```
