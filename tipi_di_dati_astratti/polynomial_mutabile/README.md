# Polynomial mutabile

Gli input numerici del protocollo pubblico sono `int` e vengono convertiti in
`BigInteger`; la rappresentazione e i risultati algebrici non sono quindi
limitati all'intervallo degli interi a 32 bit.

`MutablePolynomial` usa lo stesso stato astratto del polinomio immutabile, ma il protocollo comprende i modificatori `setCoefficient`, `addInPlace`, `multiplyInPlace` e `clear`. Il nome dei metodi rende visibile che cambia l'oggetto ricevente.

## Contratti temporali e alias

Le POST dei modificatori usano concettualmente `old(...)`. `p.addInPlace(p)` raddoppia ogni vecchio coefficiente e `p.multiplyInPlace(p)` calcola il quadrato del vecchio valore. L'implementazione fotografa prima gli operandi, così l'aliasing non rende il risultato dipendente dall'ordine del ciclo.

`copy()` restituisce invece una nuova identità indipendente. Lombok genera `equals` e `hashCode` sul valore corrente: proprio perché l'oggetto è mutabile, il README e il `Main` rendono esplicito che non va modificato mentre è usato come chiave di una collezione hash.

## AF e RI

- **AF:** la cella `i` della lista posseduta è il coefficiente di `x^i`; la lista vuota è zero.
- **RI:** lista non null, elementi non null, nessuno zero finale, nessuna esposizione della lista.

Tutti i calcoli composti preparano il risultato separatamente e installano lo stato solo dopo aver completato il calcolo. Le richieste invalide lasciano quindi invariato il ricevente.

## Esecuzione

```bash
mvn -f ../pom.xml -pl polynomial_mutabile test
mvn -f ../pom.xml -pl polynomial_mutabile exec:java \
  -Dexec.mainClass=it.uniud.tda.polynomial.mutabile.Main
```
