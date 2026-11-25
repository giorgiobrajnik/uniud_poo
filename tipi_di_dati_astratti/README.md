# Tipi di dati astratti

Il progetto accompagna la dispensa [`docs/07-TDA.md`](../docs/07-TDA.md) con quattro esempi eseguibili:

- [`polynomial_immutabile`](polynomial_immutabile/README.md): un valore matematico immutabile;
- [`polynomial_mutabile`](polynomial_mutabile/README.md): lo stesso dominio con modificatori e alias;
- [`matrix`](matrix/README.md): algebra matriciale immutabile, dimensioni e copie profonde;
- [`intranet_universitaria`](intranet_universitaria/README.md): oggetti di dominio composti e un coordinatore applicativo.

Non sono usate interfacce, classi astratte, gerarchie di dominio o pattern. Le collaborazioni avvengono per composizione e delega. Lombok è impiegato soltanto per codice meccanico (`equals`, `hashCode`, `toString`): il protocollo dei TDA rimane esplicito.

## Esecuzione

Richiede JDK 21 e Maven 3.9 o successivo.

```bash
cd tipi_di_dati_astratti
mvn clean verify
```

I `Main` si eseguono, per esempio, con:

```bash
mvn -pl polynomial_immutabile exec:java \
  -Dexec.mainClass=it.uniud.tda.polynomial.immutabile.Main
```

Le asserzioni Java sono abilitate nei test. I controlli degli argomenti, invece, non dipendono da `assert`: restituiscono `Optional` quando l'assenza è normale e lanciano eccezioni quando una richiesta viola il contratto.
