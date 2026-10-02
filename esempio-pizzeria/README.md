# Esempio Pizzeria

Un sistema di gestione di una pizzeria basato su Java che dimostra i principi della Programmazione Orientata agli Oggetti (OOP). Questo progetto mostra come modellare le operazioni reali di una pizzeria utilizzando i concetti fondamentali di OOP come classi, ereditarietà, polimorfismo e incapsulamento.

## 📋 Panoramica del Progetto

**Esempio Pizzeria** è un progetto educativo che implementa un sistema di gestione di una pizzeria semplice ma completo che include:
- **Gestione Menu**: Articoli (pizze, bevande, dolci) con prezzi
- **Gestione Tavoli**: Disposizione dei posti a sedere del ristorante e assegnazioni
- **Elaborazione Ordini**: Ordini dei clienti collegati ai tavoli
- **Calcolo Conto**: Calcolo automatico dei conti per tavolo
- **Sistema Camerieri**: Assegnazioni del personale e consegna degli ordini

Il codice è progettato per imparare la Programmazione Orientata agli Oggetti in Java con uno scenario pratico e reale.

> **Nota**: Il codice attuale è stato generato da Claude a fronte del prompt riportato in [docs/prompt-per-claude.md](docs/prompt-per-claude.md), senza essere stato verificato o modificato manualmente.

## 🛠️ Prerequisiti

Prima di poter compilare ed eseguire questo progetto, è necessario installare quanto segue:

### Obbligatori
- **Java Development Kit (JDK) 21 o superiore** — per compilare ed eseguire l'applicazione
- **Apache Maven 3.9+** — per compilare, testare e pacchettizzare il progetto

### Opzionali
- **Git** — per il controllo di versione (non obbligatorio per eseguire il progetto)
- **IDE** — IntelliJ IDEA, Eclipse o VS Code (consigliato ma non obbligatorio)

---

## ✅ Verifica dei Prerequisiti

### 1. Verifica dell'Installazione di Java

Esegui il seguente comando per verificare che Java sia installato e controllare la versione:

```bash
java -version
```

**Output previsto** (dovrebbe mostrare Java 21 o superiore):
```
openjdk version "21.0.12.1" 2024-08-20
OpenJDK Runtime Environment (build 21.0.12.1+7-post-Ubuntu-0ubuntu120.04)
OpenJDK 64-Bit Server VM (build 21.0.12.1+7-post-Ubuntu-0ubuntu120.04, mixed mode, sharing)
```

✅ **Successo**: Se vedi Java 21 o superiore, Java è installato e configurato correttamente.

❌ **Problema**: Se il comando non viene trovato o mostra Java version < 21, vedi "Installazione dei Prerequisiti" di seguito.

### 2. Verifica dell'Installazione di Maven

Esegui il seguente comando per verificare che Maven sia installato e controllare la versione:

```bash
mvn --version
```

**Output previsto** (dovrebbe mostrare Maven 3.9+ e Java 21+):
```
Apache Maven 3.9.15 (57bcc0bfc2398e89fb86ac7d2e4b0150dac080ee)
Maven home: /home/user/.maven/maven-3.9.15
Java version: 21.0.12.1, vendor: Private Build
Java home: /usr/lib/jvm/java-21-openjdk-amd64
```

✅ **Successo**: Se vedi Maven 3.9+ e Java 21+, Maven è installato correttamente.

❌ **Problema**: Se il comando non viene trovato o mostra Maven 3.8 o inferiore, vedi "Installazione dei Prerequisiti" di seguito.

---

## 📦 Compilazione del Progetto

Una volta verificati i prerequisiti, compila il progetto:

```bash
mvn clean package
```

**Cosa fa**:
- `clean` — Rimuove gli artefatti di compilazione precedenti
- `package` — Compila il codice, esegue i test, crea il file JAR

**Output previsto**:
```
[INFO] BUILD SUCCESS
[INFO] Total time:  X.XXs
```

**File di output**:
- `target/esempio-pizzeria-1.0.0.jar` — File JAR eseguibile
- `target/classes/` — File .class compilati

---

## 🧪 Esecuzione dei Test

### Esegui Tutti i Test
```bash
mvn clean test
```

**Output previsto**:
```
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Esegui una Classe di Test Specifica
```bash
mvn test -Dtest=MenuItemTest
```

### Esegui un Metodo di Test Specifico
```bash
mvn test -Dtest=MenuItemTest#testCreation
```

### Visualizza la Copertura dei Test
```bash
mvn clean test -Djacoco.skip=false
```

### Classi di Test
Il progetto include le seguenti classi di test:
- `MenuItemTest` — Test per gli articoli del menu (pizze, bevande, ecc.)
- `OrderTest` — Test per la creazione e la gestione degli ordini
- `PizzaManagerTest` — Test per il sistema principale di gestione della pizzeria
- `TableTest` — Test per la gestione dei tavoli
- `WaiterTest` — Test per le assegnazioni dei camerieri
- `AllTests` — Esecutore della suite di test

---

## ▶️ Esecuzione dell'Applicazione

### Esegui dalle Classi Compilate
```bash
mvn clean compile exec:java -Dexec.mainClass="PizzaManagerDemo"
```

Questo compila il codice ed esegue direttamente l'applicazione demo principale.

### Esegui dal JAR Pacchettizzato
Prima, pacchettizza il progetto (crea un JAR con tutte le dipendenze):
```bash
mvn clean package
java -jar target/esempio-pizzeria-1.0.0.jar
```

### Output Previsto
L'applicazione dimostra:
1. Visualizzazione del menu delle pizze
2. Assegnazioni dei tavoli nel ristorante
3. Elaborazione degli ordini di esempio
4. Elenco degli ordini attivi
5. Calcolo del conto per un tavolo specifico

Esempio di output:
```
=== DEMO SISTEMA PIZZAMANAGER ===

=== MENU ===
1. Pizza Margherita - €8.50
2. Pizza Quattro Formaggi - €9.50
...

=== TAVOLI E CAMERIERI ===
Tavolo 1: Cameriere Marco
Tavolo 2: Cameriera Giulia
...

=== ORDINI ATTIVI ===
Ordine al Tavolo 1: Pizza Margherita, Sprite (Totale: €12.00)
...

=== CONTO TAVOLO 5 ===
Totale: €45.75
```

---

## 📁 Struttura del Progetto

```
esempio-pizzeria/
├── pom.xml                          # File di configurazione Maven
├── README.md                        # Questo file
└── src/
    ├── main/
    │   └── java/
    │       ├── MenuItem.java        # Articolo del menu (pizza, bevanda, dolce)
    │       ├── Order.java           # Ordine del cliente
    │       ├── PizzaManager.java    # Classe gestore principale
    │       ├── PizzaManagerDemo.java # Demo/punto di ingresso
    │       ├── Table.java           # Tavolo del ristorante
    │       └── Waiter.java          # Cameriere/server
    └── test/
        └── java/
            ├── AllTests.java        # Suite di test
            ├── MenuItemTest.java
            ├── OrderTest.java
            ├── PizzaManagerTest.java
            ├── TableTest.java
            └── WaiterTest.java
```

---

## 🔍 Comprensione del Codice

### Classi Principali

#### MenuItem
Rappresenta qualsiasi articolo nel menu (pizza, bevanda, dolce).
```java
MenuItem pizza = new MenuItem("Pizza Margherita", 8.50, "Pizza");
```

#### Order
Rappresenta un ordine del cliente contenente più articoli del menu.
```java
Order order = new Order();
order.addItem(pizza);
```

#### Table
Rappresenta un tavolo del ristorante con capacità assegnata e cameriere.
```java
Table table = new Table(5, 4); // Tavolo per 5 persone, assegnato al cameriere 4
```

#### Waiter
Rappresenta il personale del ristorante che gestisce gli ordini.
```java
Waiter waiter = new Waiter("Marco", "marco@pizzeria.it");
```

#### PizzaManager
Classe orchestratrice principale che gestisce l'intero sistema (menu, tavoli, ordini, conti).
```java
PizzaManager manager = new PizzaManager();
manager.displayMenu();
manager.displayTableAssignments();
```

---

## 📊 Profili di Compilazione e Opzioni

### Compilazione con Diverse Opzioni

**Salta i test** (utile per compilazioni veloci):
```bash
mvn clean package -DskipTests
```

**Output verboso** (per il debug):
```bash
mvn clean package -X
```

**Compilazione offline** (usa le dipendenze in cache):
```bash
mvn clean package -o
```

**Compilazioni parallele** (più veloce per progetti grandi):
```bash
mvn clean package -T 1C
```

---

## ⚙️ Riepilogo dei Requisiti di Sistema

| Componente | Minimo | Consigliato | Nota |
|-----------|---------|-------------|------|
| Java | 21 | 21+ | Il progetto è mirato a Java 21 LTS |
| Maven | 3.9 | 3.9+ | Maven 3.8 è EOL, non usare |
| RAM | 512 MB | 2+ GB | Per la compilazione e l'esecuzione |
| Spazio su Disco | 500 MB | 1+ GB | Per le dipendenze e gli artefatti di compilazione |
| SO | Qualsiasi | Linux/macOS/Windows | Indipendente dalla piattaforma |

---

## 🐛 Risoluzione dei Problemi

### Problema: "mvn: command not found"
**Soluzione**: 
- Maven non è installato o non è in PATH
- Esegui `which mvn` per verificare se è installato
- Vedi la sezione "Installazione dei Prerequisiti" sopra

### Problema: "java: command not found"
**Soluzione**:
- Java non è installato o non è in PATH
- Esegui `which java` per verificare se è installato
- Assicurati di avere Java 21+, non una versione più vecchia

### Problema: "Maven requires Java 21 or higher"
**Soluzione**:
- La tua versione di Java è troppo vecchia
- Controlla con `java -version`
- Installa Java 21 dalla sezione dei prerequisiti

### Problema: I test falliscono dopo la compilazione
**Soluzione**:
- Assicurati di usare Java 21: `java -version`
- Prova una ricompilazione pulita: `mvn clean test`
- Verifica che non ci siano modifiche non salvate nel codice

### Problema: Il JAR non si esegue
**Soluzione**:
- Assicurati di aver compilato con: `mvn clean package`
- Verifica che il file esista: `ls -la target/esempio-pizzeria-1.0.0.jar`
- Esegui con: `java -jar target/esempio-pizzeria-1.0.0.jar`

---

## 📝 Flusso di Lavoro Tipico

Ecco un tipico flusso di lavoro di sviluppo:

```bash
# 1. Verifica i prerequisiti
java -version
mvn --version

# 2. Clona/scarica il progetto (se non già fatto)
cd /path/to/esempio-pizzeria

# 3. Compila il progetto
mvn clean package

# 4. Esegui i test
mvn test

# 5. Esegui l'applicazione
mvn exec:java -Dexec.mainClass="PizzaManagerDemo"

# OPPURE esegui dal JAR
java -jar target/esempio-pizzeria-1.0.0.jar

# 6. Per lo sviluppo (testing continuo)
mvn clean test -DreuseForks=false
```

---

## 📚 Risorse di Apprendimento

Questo progetto dimostra:
- **Classi e Oggetti**: MenuItem, Table, Waiter, Order
- **Incapsulamento**: Campi privati, metodi pubblici
- **Ereditarietà**: Potenziale per articoli di menu specializzati
- **Collezioni**: Uso di ArrayList per gestire articoli e ordini
- **Modelli di Progettazione di Base**: Modello Manager (PizzaManager)
- **Test Unitari**: Casi di test JUnit 5 per tutte le classi

---

## 🔧 Funzionalità di Java 21 Utilizzate

Questo progetto è costruito con Java 21 (ultimo LTS). Le versioni future potrebbero sfruttare:
- **Virtual Threads** (Project Loom) per l'elaborazione concorrente degli ordini
- **Pattern Matching** per una logica condizionale più elegante
- **Records** per classi di dati più semplici
- **Text Blocks** per una formattazione delle stringhe più pulita

---

## 📄 Licenza

Questo è un progetto educativo per i corsi dell'Università di Udine (UniUD) sulla Programmazione Orientata agli Oggetti (POO - Programmazione Orientata agli Oggetti).

---

## ✍️ Autore

Creato come parte del corso POO presso UniUD.

**Ultimo Aggiornamento**: 2026-09-30  
**Versione Java**: 21 LTS  
**Versione Maven**: 3.9+

---

## ❓ Domande o Problemi?

Se incontri problemi:
1. Verifica i prerequisiti con la sezione ✅ Verifica dei Prerequisiti
2. Controlla la sezione Risoluzione dei Problemi
3. Esamina i log di compilazione per i messaggi di errore
4. Assicurati di usare Java 21+ e Maven 3.9+

Buona codifica! 🍕
