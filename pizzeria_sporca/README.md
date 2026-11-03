# Pizzeria volutamente fatta male

Questo sottoprogetto risolve il tema d'esame
[`pizzeria.pdf`](../docs/pizzeria.pdf), ma il codice è
deliberatamente un controesempio di clean coding e di progettazione per
contratto. È pensato come materiale da esaminare e rifattorizzare, non come
modello da imitare. Lo facciamo in una sessione di laboratorio.

La soluzione contiene intenzionalmente:

- nomi non informativi o contrari alle convenzioni (`pizzeria`, `T`, `P`, `O`,
  `X`, `t`, `c`, `m`, `o`);
- una classe che inizializza i dati, gestisce servizi e ordini, calcola conti,
  produce report e formatta l'output;
- campi pubblici e collezioni interne direttamente modificabili;
- stringhe e primitivi per categorie, stati, pagamenti e denaro;
- valori magici, duplicazione, annidamenti e metodi con body lunghi;
- nessuna specifica di precondizioni e postcondizioni.

Nonostante ciò, il comportamento richiesto dalla traccia è coperto dai test:
configurazione di 25 tavoli e 6 camerieri, disponibilità del menu, ordini in più
fasi, tavoli uniti, gruppi successivi sullo stesso tavolo, conto storico e
resoconto serale per cameriere.

Per eseguire i test dalla radice del progetto:

```bash
mvn test
```

Il main dimostrativo si esegue, dopo la compilazione, con:

```bash
java -cp target/classes it.universita.pizzeria.Main
```
