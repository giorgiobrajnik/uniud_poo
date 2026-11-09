# Gestione degli errori, eccezioni e contratti


> Dispensa per il corso di Programmazione Orientata agli Oggetti e Laboratorio  
> Giorgio Brajnik - Novembre 2026


> Questa dispensa raccoglie alcuni aspetti fondamentali per chi impara a programmare. Potrebbe contenere semplificazioni o omissioni: si consiglia di integrarla con la documentazione ufficiale e con altri testi di riferimento.

> **Nota sugli esempi.** Gli esempi continuano a usare il sistema universitario fittizio della dispensa sui contratti: carriere, appelli e prenotazioni non descrivono il codice reale di Esse3. Salvo indicazione contraria, i parametri non sono `null`.

---

# Per approfondire

## Libri di riferimento

- B. Liskov, J. Guttag, *Program Development in Java: Abstraction, Specification, and Object-Oriented Design*, Addison-Wesley, 2001 (cap. 3 e 10). Distingue procedure totali e parziali e tratta le eccezioni come parte della specifica.
- J. Bloch, *Effective Java*, 3rd ed., Addison-Wesley, 2018 (cap. 10, «Exceptions»). Discute uso appropriato, traduzione, documentazione e scelta tra eccezioni checked e unchecked.
- R. C. Martin, *Clean Code*, Prentice Hall, 2008 (cap. 7, «Error Handling»). Presenta la gestione degli errori come responsabilità separata dalla logica principale.

## Documentazione Java

- [Exceptions](https://docs.oracle.com/javase/tutorial/essential/exceptions/): panoramica di `try`, `catch`, `finally`, propagazione e `try`-with-resources.
- [Java Language Specification, Exceptions](https://docs.oracle.com/javase/specs/jls/se21/html/jls-11.html): regole formali sulle eccezioni checked e unchecked.
- [Throwable](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Throwable.html): gerarchia, causa e eccezioni soppresse.

---

# 1. Gli errori fanno parte del comportamento

Un metodo che esegue un'operazione  può incontrare situazioni nelle quali non produce il risultato principale:

- la matricola cercata non esiste;
- l'appello è pieno;
- un voto è fuori dall'intervallo ammesso;
- il file da importare non è leggibile;
- il servizio remoto non risponde;
- una struttura dati interna è incoerente.

Dire soltanto «il metodo gestisce l'errore» non basta. Per ogni situazione servono almeno quattro decisioni:

1. è un caso **normale del dominio**, un errore del chiamante o un guasto/problema esterno?
2. chi possiede le informazioni necessarie per decidere che cosa fare?
3. come viene comunicato l'esito: valore restituito o eccezione?
4. quale stato rimane osservabile dopo il fallimento?

La gestione degli errori deve essere parte del **contratto**, non un'aggiunta da inserire alla fine nel corpo del metodo.

## 1.1 Tre categorie da non confondere

### Esito alternativo normale

Una ricerca senza risultati, un tentativo di prenotazione quando i posti sono terminati e una credenziale rifiutata sono eventi possibili che vanno previsti. Non indicano necessariamente un difetto nel programma (potrebbero essere una conseguenza di qualche errore del chiamante, dei dati forniti dall'utente, di apparecchiature esterne, di un nodo nella rete che si scollega, ecc.).

```java
Optional<Student> student = registry.findStudent(matricola);
```

> **Inserto — Che cos'è `Optional<Student>`?**
>
> `Optional<T>` è un contenitore generico che può contenere un valore di tipo `T` oppure essere vuoto. In questo caso, `Optional<Student>` segnala che la ricerca può produrre uno `Student`, se la matricola esiste, oppure nessuno studente, senza rappresentare l'assenza con `null`. La variabile `student`, quindi, non contiene direttamente uno `Student`: contiene un risultato che deve essere controllato e gestito esplicitamente.
>
> Per eseguire azioni diverse nei due casi si può usare `ifPresentOrElse`:
>
> ```java
> student.ifPresentOrElse(
>         foundStudent -> System.out.println(foundStudent),
>         () -> System.out.println("Matricola non trovata"));
> ```
>
> Le espressioni con la freccia `->` sono **espressioni lambda**: piccoli blocchi di comportamento che possono essere passati come argomenti a un metodo. A sinistra della freccia si scrivono i parametri, a destra l'operazione da eseguire. La prima lambda riceve in `foundStudent` lo `Student` contenuto nell'`Optional` e lo stampa; la seconda non riceve parametri, perciò usa le parentesi vuote `()`, e stampa il messaggio previsto per il caso di assenza. `ifPresentOrElse` esegue soltanto una delle due lambda, in base al contenuto dell'`Optional`.
>
> Se invece l'operazione corrente non può proseguire senza lo studente, conviene usare `orElseThrow` che estrae il valore presente oppure solleva un'eccezione:
>
> ```java
> Student foundStudent = student.orElseThrow(
>         () -> new IllegalStateException("Studente richiesto ma assente"));
> ```
>
> Anche `() -> new IllegalStateException(...)` è una lambda senza parametri. In questo caso produce e restituisce l'oggetto eccezione; `orElseThrow` la esegue soltanto quando l'`Optional` è vuoto.
>
> La scelta dipende dal contratto del metodo: nel primo esempio l'assenza viene gestita come esito normale; nel secondo impedisce di continuare. La sezione 2.3 approfondisce l'uso di `Optional<T>`.


### Violazione di una precondizione

Passare un voto `35` a un metodo che richiede `18 <= grade <= 30` è un errore del chiamante. 

```java
if (grade < 18 || grade > 30) {
    throw new IllegalArgumentException("grade fuori intervallo: " + grade);
}
```

Il controllo difensivo (in un certo senso, il *blindare* il mio metodo) rende il difetto visibile vicino alla sua origine; non trasforma `35` in un normale esito del dominio. Blindo il mio metodo perché sono scettico sulla validità dei dati in ingresso, e in modo che eventuali errori vengano rilevati subito e quindi il resto del codice possa assumere la validità dei dati.

### Fallimento operativo

Il programma ha ricevuto dati sensati, ma un'operazione necessaria non riesce: il file non è leggibile, il database è irraggiungibile, il formato importato è corrotto. Il chiamante può dover riprovare, mostrare un messaggio o annullare l'operazione.

```java
try {
    importService.importGrades(path);
} catch (IOException exception) {
    showMessage("Non è possibile leggere il file selezionato");
}
```
L'identificazione di queste condizioni di errore, la loro gestione, e il ripristino delle funzionalità sono i mezzi principali per migliorare la **robustezza** del programma, la sua capacità di **gestire errori**, la sua **affidabilità** e la sua **resilienza** (ciascuno dei termini in grassetto è un aspetto della qualità del software e sono delle categorie di requisiti non funzionali).

## 1.2 Segnalare non significa gestire

Un metodo che restituisce `false` o lancia un'eccezione **segnala** una condizione. La gestione avviene solo quando un livello del programma prende una decisione utile:

- propone un altro appello;
- chiede all'utente di scegliere un file diverso;
- annulla una transazione;
- riprova secondo una politica stabilita;
- termina l'operazione mostrando una spiegazione.

Stampare una traccia e continuare spesso non risolve nulla. 

---

# 2. Programmazione difensiva con valori restituiti

La programmazione difensiva rende espliciti gli input anomali e gli stati inattesi. Ci sono vari modi per farlo. Una possibilità è mappare ogni esito in un **valore restituito** dal metodo.

## 2.1 Valori sentinella

Una sentinella è un valore ordinario riservato a significare «nessun risultato»:

```java
/**
 * PRE: nessuna.
 * POST:
 *  - se matricole contiene matricola, result è un indice i tale che
 *    matricole[i] == matricola;
 *  - altrimenti result == -1.
 */
public static int findMatricola(int[] matricole, int matricola)
```

`-1` funziona come sentinella perché non è un indice valido; stiamo estendendo il codominio della funzione calcolata. È una soluzione compatta, ma ha limiti:

- il tipo `int` non distingue un indice da un errore;
- il chiamante può dimenticare il controllo;
- con più motivi di fallimento servirebbero più numeri convenzionali;
- la convenzione è comprensibile solo leggendo il contratto.

Il problema è più grave quando la sentinella può essere confusa con un risultato valido. Restituire `0.0` per la media di una carriera vuota inventa un voto plausibile. Restituire `null` sposta il fallimento in un punto successivo, dove apparirà come `NullPointerException` e sarà più difficile risalire alla causa.

## 2.2 `boolean`: successo o fallimento

Un `command` può comunicare il successo con un booleano:

```java
/**
 * Tenta di prenotare lo studente.
 *
 * PRE: nessuna.
 * POST:
 *  - restituisce result è true se e solo se, prima della chiamata, lo studente
 *    poteva essere prenotato;
 *  - se result è true, isBooked(student) è true e bookedCount()
 *    == old(bookedCount()) + 1;
 *  - se result è false, lo stato è invariato.
 */
public boolean tryBook(Student student, LocalDate today)
```

Il prefisso `try` segnala che il fallimento è previsto. Il contratto deve dire che cosa significa `false` e quale stato rimane: «restituisce `true` se tutto va bene» è troppo vago.

Il booleano non è sufficiente se il chiamante deve distinguere appello chiuso, posti esauriti e studente già prenotato.

## 2.3 `Optional<T>`: risultato presente o assente

`Optional<T>` rappresenta 0 o 1 risultato:

```java
/**
 * Cerca uno studente per matricola.
 *
 * PRE: nessuna.
 * POST:
 *  - se esiste lo studente cercato, result contiene quello studente;
 *  - altrimenti result è vuoto;
 *  - il registro è invariato.
 */
public Optional<Student> findStudent(String matricola)
```

È adatto quando l'unica informazione necessaria è «presente/assente». Non spiega *perché* il risultato manca e non va usato come contenitore generico di ogni errore.

Il chiamante riceve un `Optional<Student>`, non direttamente uno `Student`. Il compilatore impedisce quindi di trattare il risultato come se lo studente fosse certamente presente:

```java
Student student = registry.findStudent(matricola); // Errore: i tipi non sono compatibili
```

Per verificare esplicitamente il contenuto si possono usare `isPresent()` e
`isEmpty()`. Il primo restituisce `true` quando il valore è presente; il secondo
restituisce `true` quando il contenitore è vuoto. Le due condizioni sono
complementari:

```java
Optional<Student> result = registry.findStudent(matricola);
if (result.isEmpty()) {
    showMessage("Matricola non trovata");
    return;
}

Student student = result.get();
showStudent(student);
```

In questo caso `get()` è sicuro perché viene eseguito soltanto dopo aver escluso
con `isEmpty()` che l'`Optional` sia vuoto. Chiamare `get()` senza una verifica
precedente potrebbe invece produrre `NoSuchElementException`.

Per usare lo studente, il chiamante deve quindi passare attraverso uno dei
metodi di `Optional` e decidere che cosa fare quando il contenitore è vuoto.
Il metodo `ifPresentOrElse` permette di esprimere le stesse due possibilità
senza chiamare direttamente `get()`:

```java
registry.findStudent(matricola)
        .ifPresentOrElse(
                this::showStudent,
                () -> showMessage("Matricola non trovata"));
```

Il tipo, dunque, non garantisce che l'assenza venga gestita nel modo corretto, ma la rende visibile e ostacola l'uso accidentale di un valore che potrebbe non esserci.

## 2.4 Un `enum`: un insieme chiuso di esiti

Quando il metodo deve comunicare soltanto quale esito si è verificato, senza restituire anche dati associati all'esito, può usare un'**enumerazione** (`enum`). Ogni costante dell'enumerazione dà un nome a una delle alternative previste. Per esempio, un tentativo di prenotazione deve indicare se la prenotazione è riuscita, se lo studente era già prenotato, se l'appello era chiuso o se i posti erano esauriti; non deve restituire anche un altro oggetto o valore:

```java
public enum BookingOutcome {
    BOOKED,
    ALREADY_BOOKED,
    SESSION_CLOSED,
    SESSION_FULL
}
```

```java
public BookingOutcome tryBook(Student student, LocalDate today)
```

Rispetto a codici numerici come `0`, `1`, `2`, i valori sono leggibili e il compilatore impedisce valori estranei. Un `switch` rende operazionale la decisione del chiamante:

```java
BookingOutcome outcome = session.tryBook(student, today);
switch (outcome) {
    case BOOKED -> showMessage("Prenotazione completata");
    case ALREADY_BOOKED -> showMessage("Sei già prenotato");
    case SESSION_CLOSED -> showMessage("Iscrizioni chiuse");
    case SESSION_FULL -> showMessage("Posti esauriti");
}
```

## 2.5 Un oggetto risultato: esito e informazioni

Se il chiamante necessita sia dell'esito sia di dati, si può introdurre un tipo dedicato:

```java
public record ImportReport(
        int acceptedCount,
        List<RejectedRow> rejectedRows) {
}
```

```java
/**
 * Importa gli esiti validi e riferisce quelli rifiutati.
 *
 * PRE: nessuna.
 * POST:
 *  - acceptedCount è il numero di righe registrate;
 *  - rejectedRows contiene ogni riga non registrata e il motivo;
 *  - ogni riga compare esattamente in uno dei due gruppi;
 *  - gli esiti rifiutati non modificano alcuna carriera.
 */
public ImportReport importGrades(List<GradeRow> rows)
```

Il tipo risultato è spesso la scelta migliore per operazioni di dominio con fallimento parziale. Rende il caso anomalo parte esplicita dell'API e permette di aggiungere informazioni senza inventare sentinelle.

## 2.6 Il costo dei valori restituiti

I valori rendono il flusso visibile e sono comodi per gli esiti attesi, ma hanno un costo:

- ogni livello intermedio deve controllarli e propagarli;
- un valore può essere ignorato senza avvisi del compilatore;
- il valore di successo e le informazioni d'errore devono condividere lo stesso tipo risultato;
- la logica principale del chiamante può riempirsi di `if` che inoltrano l'errore.

Nell'esempio seguente il risultato dell'analisi è rappresentato da questo tipo:

```java
/**
 * Rappresenta il risultato dell'analisi del file da importare.
 *
 * @param rows righe analizzate, presenti in caso di successo
 * @param errors errori rilevati, presenti in caso di fallimento
 */
public record ImportResult(
        List<GradeRow> rows,
        List<String> errors) {

    // Il costruttore compatto salva copie non modificabili delle liste
    public ImportResult {
        rows = List.copyOf(rows);
        errors = List.copyOf(errors);
    }

    public boolean isSuccess() {
        return errors.isEmpty();
    }

    /**
     * Crea un risultato riuscito con le righe analizzate e nessun errore.
     *
     * @param rows righe prodotte dall'analisi
     * @return un risultato riuscito
     */
    public static ImportResult success(List<GradeRow> rows) {
        return new ImportResult(rows, List.of());
    }

    // Crea un risultato fallito con gli errori e nessuna riga utilizzabile.
    public static ImportResult failure(List<String> errors) {
        return new ImportResult(List.of(), errors);
    }

    // Converte un fallimento di validazione in un fallimento d'importazione.
    public static ImportResult from(ValidationResult validation) {
        return failure(validation.errors());
    }
}
```

Un risultato riuscito contiene le righe analizzate e nessun errore; un fallimento contiene gli errori e nessuna riga. Il record genera inoltre automaticamente i metodi `rows()` ed `errors()`, che restituiscono i due componenti dichiarati nell'intestazione. `ValidationResult` è un tipo analogo, che espone `isSuccess()` ed `errors()`.

```java
ImportResult result = parser.parse(path);
if (!result.isSuccess()) {
    return result;
}
ValidationResult validation = validator.validate(result.rows());
if (!validation.isSuccess()) {
    return ImportResult.from(validation);
}
```

Le eccezioni, descritte qui sotto, offrono un canale separato, particolarmente utile quando molti livelli non sanno gestire il problema.

---

# 3. Le eccezioni per esplicitare il secondo esito del metodo

Un metodo può terminare in due modi:

1. **normalmente**, eseguendo fino in fondo o incontrando `return`;
2. **eccezionalmente**, lanciando un oggetto che deriva da `Throwable`.

Quattro costrutti collaborano per segnalare e gestire il secondo esito:

- `throw` segnala il fallimento e lancia un oggetto eccezione;
- `try` delimita le istruzioni che potrebbero terminare eccezionalmente;
- `catch` intercetta un tipo compatibile di eccezione e stabilisce come gestirlo;
- `finally` introduce istruzioni da eseguire comunque al termine del `try`,
  sia in caso di successo sia in caso di eccezione.

Il metodo seguente cerca uno studente. Se la matricola non esiste, non può
restituire il risultato promesso e usa `throw`:

```java
public Student requireStudent(String matricola) {
    Optional<Student> student = findStudent(matricola);
    if (student.isEmpty()) {
        throw new StudentNotFoundException(matricola);
    }
    return student.get();
}
```

`throw` interrompe immediatamente il flusso normale del metodo: le istruzioni
successive non vengono eseguite e non viene restituito alcun valore normale.
L'oggetto `StudentNotFoundException` trasporta invece l'informazione sul
fallimento.

Un chiamante può gestire questo secondo esito con `try` e `catch`:

```java
public void printStudent(String matricola) {
    try {
        Student student = requireStudent(matricola);
        System.out.println(student);
    } catch (StudentNotFoundException exception) {
        System.err.println("Studente non trovato: " + matricola);
    } finally {
        System.out.println("Ricerca terminata");
    }
}
```

Se `requireStudent` esegue normalmente, viene eseguita la stampa e il
`catch` viene saltato. Se invece lancia `StudentNotFoundException`, Java salta
le istruzioni rimanenti del `try` ed esegue il `catch` compatibile. Terminato il
`try` o l'eventuale `catch`, Java esegue in entrambi i casi il blocco `finally`.
Solo dopo prosegue con le istruzioni successive oppure, se l'eccezione non è
stata intercettata, ne continua la propagazione. Il `finally` è utile per
ripristinare condizioni che devono valere comunque; per chiudere risorse come
file o connessioni si preferisce invece, quando applicabile, il
`try`-with-resources descritto più avanti.

Lo stesso metodo `requireStudent` può essere scritto in forma compatta con
`Optional.orElseThrow`:

```java
public Student requireStudent(String matricola) {
    return findStudent(matricola)
            .orElseThrow(() -> new StudentNotFoundException(matricola));
}
```

In questa variante la parola chiave `throw` non compare direttamente: la lambda
costruisce l'eccezione e, se l'`Optional` è vuoto, `orElseThrow` la lancia.

## 3.1 Propagazione lungo le chiamate

Supponiamo questa catena:

```text
buttonClicked -> importGrades -> readRows -> Files.readAllLines
```

Se `Files.readAllLines` lancia `IOException`, Java cerca un `catch` compatibile:

1. nel `try` che contiene la chiamata corrente;
2. se non lo trova, termina quel metodo e risale al chiamante;
3. continua fino a trovare un gestore;
4. se nessuno gestisce l'eccezione, la macchina virtuale JVM termina il thread e stampa la traccia.


## 3.2 Informazioni contenute

Un'eccezione può comunicare:

- il **tipo** di problema (`IOException`, `InvalidGradeException`);
- un messaggio destinato a chi diagnostica;
- dati specifici, tramite campi e metodi;
- la **causa** originale;
- la traccia delle chiamate attraversate.

```java
public final class InvalidGradeException extends IllegalArgumentException {
    private final int grade;

    public InvalidGradeException(int grade) {
        super("Voto fuori intervallo [18, 30]: " + grade);
        this.grade = grade;
    }

    public int grade() {
        return grade;
    }
}
```

Il tipo dell'eccezionepermette al programma di scegliere (*programmaticamente*); il messaggio aiuta la diagnosi per l'essere umano. Non
conviene basare la logica sul testo del messaggio, che può cambiare o essere
tradotto. Questo codice, per esempio, è fragile:

```java
try {
    registerGrade(student, grade);
} catch (IllegalArgumentException exception) {
    if (exception.getMessage().contains("Voto fuori intervallo")) {
        showMessage("Correggere il voto");
    }
}
```

La decisione dipende dalle parole `"Voto fuori intervallo"`. Se il messaggio
viene riformulato o tradotto, la condizione diventa falsa anche se il problema è
lo stesso. Inoltre, il `catch` generico non distingue questa violazione da altre
`IllegalArgumentException`.

La versione robusta seleziona il tipo specifico e legge il voto dal campo
strutturato dell'eccezione:

```java
try {
    registerGrade(student, grade);
} catch (InvalidGradeException exception) {
    showMessage("Voto non valido: " + exception.grade());
    System.err.println(exception.getMessage());
}
```

Qui il tipo `InvalidGradeException` determina quale gestione eseguire e
`grade()` fornisce un dato stabile alla logica del programma. Il messaggio può
essere registrato per diagnosticare il problema, senza controllare il flusso.

## 3.3 Le eccezioni non sono salti generici

Un'eccezione separa la logica principale dal fallimento, ma rende il trasferimento di controllo meno locale. Va usata per condizioni nelle quali il metodo non può produrre il proprio risultato, non per sostituire normali `if`, cicli o casi frequenti.

Controesempio:

```java
try {
    while (true) {
        process(grades.get(index));
        index++;
    }
} catch (IndexOutOfBoundsException exception) {
    // usata per terminare il ciclo
}
```

Versione chiara:

```java
for (ExamResult grade : grades) {
    process(grade);
}
```

---

# 4. Semantica di `try`, `catch` e `finally`

## 4.1 Il blocco `try`

Il `try` delimita le istruzioni delle quali si vogliono intercettare le eccezioni:

```java
try {
    List<GradeRow> rows = reader.read(path);
    importer.importRows(rows);
    showMessage("Importazione completata");
} catch (IOException exception) {
    showMessage("Lettura del file non riuscita");
}
```

Se `reader.read` lancia `IOException`, le due istruzioni successive nel `try` vengono saltate. Se `importRows` lancia un'eccezione diversa e non compatibile con `IOException`, questo `catch` non la intercetta: l'eccezione si propaga.

Il `try` dovrebbe racchiudere solo le istruzioni per le quali quel `catch` sa
prendere una decisione corretta. Un blocco enorme rende difficile sapere quale
operazione sia fallita. Nel codice seguente sia la lettura sia la scrittura del
resoconto possono lanciare `IOException`, ma il messaggio attribuisce sempre il
problema alla lettura:

```java
try {
    List<GradeRow> rows = reader.read(sourcePath);
    importer.importRows(rows);
    reportWriter.write(reportPath, rows);
} catch (IOException exception) {
    showMessage("Impossibile leggere il file di origine");
}
```

Se fallisce `reportWriter.write`, il `catch` viene eseguito, ma comunica una
causa sbagliata. Separando le operazioni, ogni gestore può prendere una decisione
coerente con il punto in cui si è verificato il fallimento:

```java
List<GradeRow> rows;
try {
    rows = reader.read(sourcePath);
} catch (IOException exception) {
    showMessage("Impossibile leggere il file di origine");
    return;
}

importer.importRows(rows);

try {
    reportWriter.write(reportPath, rows);
} catch (IOException exception) {
    showMessage("Dati importati, ma resoconto non salvato");
}
```

La separazione rende visibili anche due conseguenze diverse: se la lettura
fallisce, l'importazione non può iniziare; se fallisce il resoconto,
l'importazione è già avvenuta e non deve essere descritta come fallita.

## 4.2 Selezione del `catch`

Java sceglie il **primo** `catch` il cui tipo è compatibile con l'oggetto lanciato:

Un `catch` è compatibile se l'oggetto lanciato ha esattamente quel tipo oppure
un suo sottotipo. Per esempio, `FileNotFoundException` è un sottotipo di
`IOException`, quindi un `catch (IOException exception)` può intercettarla:

```java
try {
    throw new FileNotFoundException("studenti.csv");
} catch (IOException exception) {
    showMessage("File non disponibile");
}
```

Il contrario non vale: un `catch (FileNotFoundException exception)` non può
intercettare una generica `IOException`, perché non tutte le `IOException` sono
dovute a un file mancante. Per questo l'ordine dei `catch` è importante: un
tipo generale posto per primo intercetterebbe anche tutte le sue eccezioni più
specifiche.

```java
try {
    importService.importGrades(path);
} catch (MalformedGradeFileException exception) {
    showMalformedRows(exception.problems());
} catch (IOException exception) {
    showMessage("Errore durante la lettura: " + exception.getMessage());
}
```

I gestori specifici devono precedere quelli generali. Il compilatore rifiuta un `catch` irraggiungibile:

```java
try {
    importService.importGrades(path);
} catch (Exception exception) {
    // intercetta già anche IOException
} catch (IOException exception) { // errore di compilazione
    // non può essere raggiunto
}
```

Un *multi-catch* riunisce tipi gestiti nello stesso modo:

```java
try {
    configuration.load(path);
} catch (IOException | ConfigurationException exception) {
    useDefaultConfiguration();
}
```

## 4.3 Dopo il `catch`

Quando il `catch` termina normalmente, l'esecuzione continua dopo l'intera istruzione `try`:

```java
try {
    importGrades(path);
} catch (IOException exception) {
    showMessage("Importazione non riuscita");
}
refreshScreen();
```

`refreshScreen()` viene eseguito sia dopo il successo sia dopo la gestione di `IOException`. Se il `catch` lancia a sua volta un'eccezione o esegue `return`, non si arriva a quella riga.

## 4.4 Il blocco `finally`

`finally` contiene codice da eseguire quando si abbandona il `try` o un suo `catch`, sia per conclusione normale sia per `return`, `break` o eccezione:

```java
Lock lock = career.lock();
lock.lock();
try {
    career.registerGrade(result);
} finally {
    lock.unlock();
}
```

Lo sblocco avviene anche se `registerGrade` lancia un'eccezione. Se non c'è eccezione, dopo `finally` il flusso continua. Se c'è un'eccezione non gestita, dopo `finally` la propagazione riprende.


## 4.5 `return` e `finally`

L'espressione di `return` viene valutata, poi si esegue `finally`, poi il metodo restituisce il valore già calcolato:

```java
public int example() {
    int value = 1;
    try {
        return value;
    } finally {
        value = 2;
    }
}
```

Il metodo restituisce `1`. Il cambiamento della variabile locale avviene troppo tardi per modificare il valore calcolato.

Un `return` o un `throw` dentro `finally` sostituisce invece il ritorno o l'eccezione precedente:

```java
try {
    throw new IOException("errore originale");
} finally {
    return; // nasconde l'eccezione: da evitare
}
```

Per questo in `finally` non si usano `return` e, per quanto possibile, non si lanciano nuove eccezioni che nascondano quella principale.

## 4.6 `try`-with-resources

Per risorse che implementano `AutoCloseable`, Java offre una forma più sicura:

```java
public List<String> readLines(Path path) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(path)) {
        return reader.lines().toList();
    }
}
```

La risorsa viene chiusa automaticamente, anche in caso di eccezione. Con più
risorse, Java le apre nell'ordine in cui sono dichiarate e le chiude in ordine
inverso, come una pila: l'ultima aperta è la prima a essere chiusa.

```java
try (InputStream input = Files.newInputStream(source);
     OutputStream output = Files.newOutputStream(destination)) {
    input.transferTo(output);
}
```

In questo esempio Java apre prima `input` e poi `output`. Quando il blocco
termina, anche per un'eccezione, invoca prima `output.close()` e poi
`input.close()`. L'ordine inverso è importante quando una risorsa dichiarata
dopo usa quella dichiarata prima: la risorsa dichiarata dopo può completare la
propria chiusura (per esempio svuotare un buffer) mentre la risorsa da cui
dipende è ancora aperta.


---

# 5. Eccezioni predefinite e nuove

## 5.1 La gerarchia delle eccezioni

```text
Throwable
+-- Error
+-- Exception
    +-- RuntimeException
    +-- altre Exception
```

- `Error` rappresenta problemi gravi dell'ambiente di esecuzione, come `OutOfMemoryError`; normalmente un'applicazione non tenta di gestirli.
- `RuntimeException` e le sue sottoclassi sono eccezioni **unchecked**.
- le altre sottoclassi di `Exception` sono eccezioni **checked**.

Non si estende direttamente `Throwable`. Per una nuova eccezione applicativa si estende in genere `Exception` oppure `RuntimeException`.

## 5.2 Eccezioni unchecked predefinite

Alcune eccezioni comuni indicano errori di programmazione:

| Eccezione | Significato tipico |
|---|---|
| `IllegalArgumentException` | un argomento non rispetta i vincoli richiesti |
| `IllegalStateException` | lo stato dell'oggetto non consente l'operazione |
| `NullPointerException` | un riferimento richiesto è `null` |
| `IndexOutOfBoundsException` | un indice è fuori dai limiti |
| `ArithmeticException` | operazione aritmetica non ammessa, per esempio divisione intera per zero |
| `UnsupportedOperationException` | l'oggetto non supporta l'operazione richiesta |

Non bisogna lanciare sempre un'eccezione istanza di una classe nuova. Se il significato standard descrive bene la violazione e non servono dati specifici, la classe predefinita rende l'API familiare a chi la usa.

```java
public void setMaximumBookings(int maximumBookings) {
    if (maximumBookings < 0) {
        throw new IllegalArgumentException(
                "maximumBookings negativo: " + maximumBookings);
    }
    this.maximumBookings = maximumBookings;
}
```

## 5.3 Eccezioni checked predefinite

Molte API usano eccezioni checked per fallimenti operativi:

- `IOException` per accesso a file e flussi;
- `SQLException` per operazioni su database;
- `ReflectiveOperationException` per operazioni di riflessione.

Il compilatore applica la regola **catch or declare**: un metodo che può lasciar uscire un'eccezione checked deve catturarla oppure dichiararla con `throws`.

```java
public List<GradeRow> readRows(Path path) throws IOException {
    return Files.readAllLines(path).stream()
            .map(this::parseRow)
            .toList();
}
```

`throws IOException` non lancia nulla da solo e non significa che ogni chiamata
fallirà. Dichiara un possibile completamento eccezionale e obbliga il chiamante
a considerarlo. In pratica, il compilatore rifiuta un metodo che chiama
`readRows` senza fare una delle due scelte:

```java
public void importFrom(Path path) {
    List<GradeRow> rows = readRows(path); // errore di compilazione
    importer.importRows(rows);
}
```

La prima scelta è intercettare l'eccezione con `catch`, quando questo livello
sa come gestirla o comunicarla:

```java
public void importFrom(Path path) {
    try {
        List<GradeRow> rows = readRows(path);
        importer.importRows(rows);
    } catch (IOException exception) {
        showMessage("Impossibile leggere il file selezionato");
    }
}
```

La seconda scelta è dichiarare a sua volta `throws IOException`, rinviando la
decisione al proprio chiamante:

```java
public List<GradeRow> loadForValidation(Path path) throws IOException {
    return readRows(path);
}
```

Anche chi chiama `loadForValidation` dovrà quindi scegliere tra `catch` e
`throws`, finché un livello gestisce l'errore oppure l'eccezione raggiunge la macchina virtuale.

## 5.4 Checked o unchecked per una nuova eccezione?

Un esempio di nuova eccezione **checked**:

```java
public final class MalformedGradeFileException extends Exception {
    public MalformedGradeFileException(String message) {
        super(message);
    }

    public MalformedGradeFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

Un esempio di una nuova eccezione **unchecked**:

```java
public final class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String matricola) {
        super("Studente non trovato: " + matricola);
    }
}
```

Domanda guida:

> È ragionevole pretendere che chi chiama gestisca subito questo caso?

Se sì, una checked rende l'obbligo visibile al compilatore. Se il chiamante non può recuperare localmente, oppure la condizione indica un difetto di programmazione, una unchecked evita `catch` puramente burocratici.

La scelta dipende dal livello dell'API. Un parser di file può dichiarare checked un formato scorretto perché il chiamante può far scegliere un altro file. Lo stesso problema, in un file interno garantito dall'applicazione, può essere una unchecked perché indica dati o distribuzione incoerenti.

Non si sceglie checked soltanto perché il problema è «grave», né unchecked soltanto per scrivere meno codice.

## 5.5 Progettare una classe di eccezione

Una buona eccezione applicativa:

- ha un nome che descrive la condizione, non l'operazione generica fallita;
- conserva la causa quando traduce un errore di livello inferiore;
- espone come dati le informazioni che un gestore deve usare;
- non include dati sensibili nel messaggio;
- rimane immutabile.

Per esempio, `BookingException` dice soltanto che un'operazione di prenotazione
non è riuscita: potrebbe trattarsi di posti esauriti, appello chiuso o studente
già prenotato. `StudentAlreadyBookedException`, invece, nomina una condizione
precisa e permette al chiamante di scegliere una gestione coerente:

```java
try {
    bookingService.book(student, session);
} catch (StudentAlreadyBookedException exception) {
    showMessage("Lo studente risulta già prenotato per questo appello");
}
```

Il nome dell'eccezione comunica quindi *che cosa* è accaduto nel dominio,
non soltanto che il metodo `book` è terminato senza successo.

```java
public final class GradeImportException extends Exception {
    private final Path source;

    public GradeImportException(Path source, Throwable cause) {
        super("Importazione non riuscita da " + source, cause);
        this.source = source;
    }

    public Path source() {
        return source;
    }
}
```

Non serve una classe diversa per ogni frase d'errore. Serve quando il chiamante deve riconoscere il caso, quando appartiene al vocabolario dell'API o quando porta dati strutturati.

---

# 6. Che cosa cambia nei contratti

Con le eccezioni, il contratto deve distinguere il completamento normale dai
possibili completamenti eccezionali. Deve descrivere almeno:

- quando il metodo termina normalmente;
- quale valore restituisce e quali modifiche produce;
- quali eccezioni possono uscire e in quali condizioni;
- quale stato rimane in ciascun completamento eccezionale.

## 6.1 Da metodo parziale a controllo difensivo

Contratto iniziale:

```java
/**
 * PRE: 18 <= result.grade() <= 30.
 * POST: il voto è registrato nella carriera.
 */
public void registerGrade(ExamResult result)
```

Fuori dalla PRE il contratto non promette nulla. Si può aggiungere un controllo difensivo senza togliere al chiamante il suo obbligo:

```java
public void registerGrade(ExamResult result) {
    if (result.grade() < 18 || result.grade() > 30) {
        throw new IllegalArgumentException(
                "Voto fuori intervallo: " + result.grade());
    }
    // registrazione
}
```

Se si vuole rendere il lancio una garanzia sulla quale il chiamante può contare, lo si scrive nel contratto. A quel punto il metodo è definito anche per il voto invalido e la condizione non è più una PRE:

```java
/**
 * PRE: nessuna.
 * Se il metodo termina normalmente:
 *  - 18 <= result.grade() <= 30;
 *  - il voto è registrato.
 * Se result.grade() < 18 oppure result.grade() > 30:
 *  - viene lanciata IllegalArgumentException;
 *  - la carriera è invariata.
 */
public void registerGrade(ExamResult result)
```

Questa seconda versione offre un comportamento definito su un dominio più ampio.

## 6.2 Eccezione come parte del risultato astratto

Consideriamo un'operazione che richiede uno studente esistente:

```java
/**
 * Restituisce lo studente con la matricola indicata.
 *
 * PRE: nessuna.
 * Se il metodo termina normalmente:
 *  - result è lo studente la cui matricola è matricola;
 *  - il registro è invariato.
 * Se non esiste uno studente con la matricola indicata:
 *  - viene lanciata StudentNotFoundException;
 *  - il registro è invariato.
 */
public Student requireStudent(String matricola)
```


La stessa operazione potrebbe essere specificata con `Optional<Student>`. La differenza non è ciò che si può rappresentare, ma l'uso previsto:

- `findStudent`: l'assenza è frequente e il chiamante decide immediatamente;
- `requireStudent`: a questo livello l'assenza impedisce di continuare e può attraversare più chiamate.

## 6.3 Stato dopo un'eccezione

Dire quale eccezione viene lanciata non basta. Un `command` può aver modificato lo stato prima di fallire.

### Garanzia forte

Se il metodo fallisce, lo stato osservabile è identico a quello iniziale:

```text
Se il salvataggio della prenotazione non viene completato:
  - viene lanciata BookingPersistenceException;
  - session e repository sono invariati.
```

Una possibile implementazione costruisce prima un nuovo stato, lo salva in una
transazione e sostituisce lo stato corrente soltanto dopo il successo:

```java
public void book(Student student) {
    BookingSession updated = session.withBooking(student);

    try {
        transaction.begin();
        repository.save(updated);
        transaction.commit();
    } catch (RepositoryException exception) {
        transaction.rollback();
        throw new BookingPersistenceException(exception);
    }

    session = updated;
}
```

`withBooking` crea `updated` senza modificare `session`. Se `repository.save`
o `transaction.commit()` falliscono, il `catch` esegue `rollback()` e
l'assegnazione finale non viene eseguita: sia il repository sia `session`
conservano lo stato iniziale. Il solo `catch` non basterebbe a offrire questa
garanzia; sono necessari una transazione o un'altra operazione di persistenza
atomica.

Dal punto di vista del chiamante, è come se l'operazione non fosse iniziata. È la garanzia più facile da usare, ma può richiedere validazione preventiva, copie o transazioni.

### Garanzia di base

Se il metodo fallisce, gli oggetti restano validi e rispettano gli invarianti, ma possono essere cambiati:

```text
Se l'invio della notifica non viene completato:
  - viene lanciata NotificationException;
  - la prenotazione è registrata;
  - gli invarianti di session e career valgono;
  - nessuna garanzia è data sull'invio della notifica, ma non lo stesso viene fatto per la registrazione che viene garantita.
```

Per esempio, `bookingService.book` può completare prima la prenotazione con la
garanzia forte descritta sopra; soltanto dopo viene tentata l'operazione
secondaria di notifica:

```java
public void bookAndNotify(Student student) {
    Booking booking = bookingService.book(student);

    try {
        notificationService.sendBookingConfirmation(booking);
    } catch (MessagingException exception) {
        throw new NotificationException(booking.id(), exception);
    }
}
```

Se `sendBookingConfirmation` fallisce, la prenotazione resta registrata e gli
oggetti coinvolti sono ancora coerenti. Non si può però dedurre che lo studente
non abbia ricevuto la notifica: un timeout può avvenire dopo che il servizio
esterno l'ha accettata. Il chiamante deve quindi riprovare o pianificare un
nuovo invio senza registrare di nuovo la prenotazione.

Il chiamante deve poter scoprire che cosa è avvenuto o eseguire una compensazione. Una POST come «può essere successo qualunque cosa» è troppo debole per un'API utile.

### Nessun fallimento osservabile

Alcune operazioni promettono di non fallire per una certa causa, perché la assorbono internamente:

```text
Se il metodo termina normalmente:
  - la registrazione è completata;
  - l'eventuale indisponibilità del servizio di statistiche non impedisce
    la registrazione ed è annotata nel log.
```

Per esempio, l'aggiornamento delle statistiche può essere un effetto
accessorio della registrazione del voto:

```java
public void registerGrade(ExamResult result) {
    career.register(result);
    repository.save(career);

    try {
        statisticsService.record(result);
    } catch (StatisticsUnavailableException exception) {
        logger.warn("Statistiche non aggiornate", exception);
    }
}
```

Se `statisticsService.record` fallisce, il `catch` assorbe soltanto questo
problema: il metodo termina normalmente perché voto e carriera sono già stati
registrati. L'assorbimento è corretto solo se le statistiche non fanno parte
del risultato promesso; non si deve invece catturare genericamente ogni
eccezione per far sembrare riuscita un'operazione principale fallita.

## 6.4 Contratto del metodo chiamante

Se `importGrades` chiama `readRows`, deve decidere che cosa fare con ogni completamento eccezionale di `readRows`, come già faceva con le sue PRE:

```java
/**
 * Legge gli esiti dal file e li importa.
 *
 * PRE: path non è null.
 * Se il metodo termina normalmente:
 *  - result descrive tutte le righe lette dal file: acceptedCount è il numero
 *    di righe registrate e rejectedRows contiene ogni riga rifiutata con il
 *    relativo motivo;
 *  - ogni riga letta compare esattamente in uno dei due gruppi.
 * Se il file non può essere letto:
 *  - viene lanciata GradeImportException;
 *  - exception.source() è path e exception.getCause() è la IOException
 *    originale;
 *  - nessuna carriera viene modificata.
 */
public ImportReport importGrades(Path path) throws GradeImportException {
    try {
        List<GradeRow> rows = reader.readRows(path);
        return importRows(rows);
    } catch (IOException exception) {
        throw new GradeImportException(path, exception);
    }
}
```

La `IOException` non compare nell'interfaccia di `importGrades`: viene **trasformata**. Il suo contratto espone `GradeImportException`, un concetto coerente con il livello di astrazione.

Per ogni eccezione di un metodo chiamato ci sono quattro possibilità:

1. assorbirla e completare normalmente;
2. catturarla e rilanciare la stessa eccezione;
3. non catturarla e lasciarla propagare;
4. catturarla e trasformarla in un'altra eccezione o in un valore.

La scelta modifica il contratto del chiamante.

## 6.5 Valori ed eccezioni a confronto nel contratto

Due versioni della prenotazione:

```java
public BookingOutcome tryBook(Student student, LocalDate today)
```

```java
public void book(Student student, LocalDate today)
        throws BookingRejectedException
```

Possono rappresentare gli stessi casi, ma assegnano oneri diversi:

| Aspetto | Valore `BookingOutcome` | Eccezione |
|---|---|---|
| Percorso normale | ogni esito passa da `return` | successo e fallimento usano canali distinti |
| Visibilità | immediata nel tipo restituito | checked nella firma; unchecked nel Javadoc |
| Possibilità di ignorare | alta: si può scartare il valore | checked bassa; unchecked alta |
| Propagazione | manuale a ogni livello | automatica fino a un `catch` |
| Uso naturale | esiti frequenti e locali | impossibilità di continuare a quel livello |

La scelta non dipende dal fatto che la condizione sia «un errore» in senso colloquiale. Dipende da frequenza, livello di astrazione, possibilità di recupero e uso previsto dell'API.

---

# 7. Assorbire, rispedire, non fare nulla, trasformare

Un `catch` è una decisione che stabilisce a quale livello appartiene il problema.

## 7.1 Assorbire: il problema è davvero risolto

Assorbire significa impedire che l'eccezione esca perché il metodo ha completato una strategia valida di rimedio.

```java
public Preferences loadPreferences(Path path) {
    try {
        return preferencesReader.read(path);
    } catch (NoSuchFileException exception) {
        return Preferences.defaults();
    }
}
```

È corretto se il contratto stabilisce che l'assenza del file produce preferenze predefinite. Il metodo non finge che la lettura sia riuscita: fornisce il risultato promesso dall'astrazione.

Altri casi sensati:

- un'operazione opzionale di telemetria fallisce ma l'operazione principale è già completa;
- un tentativo viene ripetuto secondo una politica limitata e poi riesce;
- una cache non disponibile viene sostituita dalla sorgente principale;
- un file facoltativo mancante corrisponde esplicitamente alla configurazione standard.

Assorbire è scorretto quando si perde un'informazione necessaria al chiamante:

```java
try {
    repository.save(career);
} catch (IOException exception) {
    // ignorata: il chiamante crede che il salvataggio sia riuscito
}
```

## 7.2 Rispedire: aggiungere contesto e conservare la causa

Talvolta si cattura e si rilancia la **stessa** eccezione dopo un'azione locale:

```java
try {
    repository.save(career);
} catch (IOException exception) {
    audit.recordFailure(career.student().matricola());
    throw exception;
}
```

È giustificato se il livello corrente deve eseguire qualcosa prima della propagazione. Catturare soltanto per scrivere `throw exception;` non aggiunge valore.

Non si deve creare inutilmente una nuova eccezione dello stesso tipo:

```java
catch (IOException exception) {
    throw new IOException(exception.getMessage()); // perde causa e contesto
}
```

Viene copiata soltanto una stringa, che può anche essere `null`. Se l'eccezione
originale era, per esempio, una `NoSuchFileException`, la nuova eccezione perde:

- il tipo specifico, quindi un chiamante non può più intercettare il caso
  «file mancante» separatamente dalle altre `IOException`;
- i dati strutturati dell'oggetto originale, come il percorso restituito da
  `NoSuchFileException.getFile()`;
- la traccia che indica il punto in cui il problema è nato: la nuova traccia
  inizia nel `catch`;
- il collegamento all'eccezione originale, perché `getCause()` restituisce
  `null`.

Se serve aggiungere contesto, lo si può fare conservando la causa:

```java
catch (IOException exception) {
    throw new IOException(
            "Lettura degli esiti da " + path + " non riuscita",
            exception);
}
```

## 7.3 Non fare nulla: lasciare propagare

Spesso la scelta migliore è non scrivere alcun `catch` al livello che non sa decidere:

```java
public List<GradeRow> load(Path path) throws IOException {
    return reader.readRows(path);
}
```



## 7.4 Trasformare: cambiare livello di astrazione

Una libreria di persistenza può lanciare `SQLException`, ma il servizio di gestione delle carriere degli studenti non dovrebbe obbligare i chiamanti a conoscere il database scelto:

```java
public void registerGrade(ExamResult result) {
    try {
        repository.save(result);
    } catch (SQLException exception) {
        throw new GradeRegistrationException(result.course(), exception);
    }
}
```

Qui *tradurre* un'eccezione significa catturare `SQLException`, un dettaglio
tecnico del database, e lanciare al suo posto `GradeRegistrationException`, un
problema espresso nel linguaggio del servizio. Il chiamante vede la seconda;
la prima resta disponibile come causa tramite `getCause()`.

Questa traduzione:

- mantiene l'interfaccia indipendente dall'infrastruttura;
- usa il vocabolario del livello corrente;
- conserva l'eccezione originale come causa;
- non cambia checked in unchecked senza una ragione di contratto.

Si può trasformare anche un'eccezione in un valore quando il caso diventa normale a quel livello:

```java
public Optional<Student> findStudent(String matricola) {
    try {
        return Optional.of(repository.requireStudent(matricola));
    } catch (RecordNotFoundException exception) {
        return Optional.empty();
    }
}
```

Oppure si può trasformare un valore in un'eccezione, come `Optional.orElseThrow`, quando l'assenza del risultato impedisce all'operazione superiore di continuare.
