# Pizzeria pulita: contratti ed eccezioni

Soluzione Java 21 del tema [`pizzeria.pdf`](../docs/pizzeria.pdf). Il progetto
modella i 25 tavoli, i 6 camerieri, il menu, i servizi distinti per gruppo di
clienti, gli ordini in più fasi, i conti storici e il resoconto serale.

## Scelte di progetto

- `BigDecimal` rappresenta il denaro e le righe d'ordine congelano il prezzo
  applicato al momento dell'ordine.
- Enum sostituiscono stringhe e codici magici per area, categoria, stato e
  pagamento.
- Le collezioni esposte sono copie non modificabili; Lombok elimina boilerplate
  nelle entità valore senza nascondere le regole di dominio.
- I metodi pubblici principali specificano PRE, POST, invarianza e comportamento
  di fallimento nel Javadoc.
- Le operazioni di pagamento verificano tutte le condizioni prima di mutare lo
  stato (garanzia forte).
- L'esportatore ripristina in `finally` il proprio conteggio operativo.

## Strategie di segnalazione mostrate

| Strategia | Esempio | Significato |
|---|---|---|
| unchecked | `PietanzaNonDisponibileException`, `TavoloOccupatoException` | comando incompatibile con lo stato corrente |
| checked | `EsportazioneResocontoException` | fallimento I/O che il chiamante deve gestire o propagare |

## Strumenti usati

### Lombok

[Lombok](https://projectlombok.org/) è una libreria che genera codice ripetitivo
(*boilerplate*) durante la compilazione, a partire da annotazioni. Qui è usata per
evitare di scrivere a mano getter, `equals`/`hashCode` e `toString`:

- `@Getter` genera i metodi `getX()` per i campi della classe;
- `@Value` rende la classe immutabile: campi `private final`, getter, `equals`,
  `hashCode` e `toString`;
- `@EqualsAndHashCode(of = "codice")` e `@ToString` generano questi metodi, nel primo
  caso confrontando solo i campi indicati.

Il codice generato non compare nei sorgenti, ma solo nelle classi compilate. Lombok è
dichiarato nel [pom.xml](pom.xml) come dipendenza e come *annotation processor* del
compilatore; l'IDE ha bisogno del suo plugin per riconoscere i metodi generati.
Documentazione: <https://projectlombok.org/features/>.

### JaCoCo

[JaCoCo](https://www.jacoco.org/jacoco/) misura la **copertura del codice**, cioè
quali righe e quali rami (`if`, `switch`, cicli) del programma sono stati eseguiti dai
test. Serve a scoprire le parti non provate. Funziona così:

1. durante i test un *agent* registra ciò che viene eseguito (`jacoco.exec`);
2. nella fase `verify` produce un report HTML;
3. controlla una soglia: qui il build **fallisce se la copertura dei rami (branch)
   scende sotto il 90%**.

Uso:

```bash
mvn clean verify
```

Il report si apre da `target/site/jacoco/index.html` (per esempio con
`xdg-open target/site/jacoco/index.html`): ogni classe ha una riga con le percentuali,
e nei sorgenti le righe sono colorate (verde coperta, giallo parziale, rosso non
eseguita). Per eseguire solo i test, senza report né soglia, basta `mvn test`.

### Javadoc

Javadoc è lo strumento standard di Java che genera la **documentazione HTML** a partire
dai commenti `/** ... */` posti sopra classi e metodi. In questo progetto i commenti
descrivono stato astratto e contratti (`PRE`/`POST`). Con il tag `{@summary ...}` il
riepilogo di ogni metodo mostra sia la precondizione sia la postcondizione.

Uso:

```bash
mvn javadoc:javadoc
```

La documentazione si trova in `target/reports/apidocs/index.html`. Per impostazione
del `pom.xml` mostra i membri pubblici e package-private (quindi anche `Ordine`,
`ServizioTavolo` e i registri); per includere anche i privati aggiungere
`-Dshow=private`. Se la pagina sembra vecchia, cancellare prima la cartella:
`rm -rf target/reports/apidocs`.

## Compilazione, test e copertura

```bash
cd pizzeria_pulita_eccezioni
mvn clean verify
```

Il goal `verify` compila, esegue i test, genera il report JaCoCo in
`target/site/jacoco/index.html` e fallisce sotto il 90% di branch coverage.

Per eseguire il main dimostrativo:

```bash
mvn package
java -cp target/classes it.uniud.pizzeria.Main
```
