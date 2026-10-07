# 01 - Convenzioni, package, visibilità e blocchi

Esempi (✅) e controesempi (❌) su: convenzioni sui nomi, package e import, visibilità, blocchi.
Ogni cartella sotto `src/main/java/it/universita/esse3/` è un argomento e ha un `main` eseguibile.

## Indice

### `nomi/` - convenzioni sui nomi (dispensa §2)

Esecuzione: [EsempioNomi](src/main/java/it/universita/esse3/nomi/EsempioNomi.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | [CarrieraStudente](src/main/java/it/universita/esse3/nomi/CarrieraStudente.java) | classe, campo, costante `static final` e metodo con nomi convenzionali |
| ❌ | [NomiNonConvenzionali](src/main/java/it/universita/esse3/nomi/NomiNonConvenzionali.java) | `NumeroCFU` è valido ma non convenzionale; `studente`, `Studente`, `STUDENTE` sono identificatori diversi |

### `omonimie/` - package e import (dispensa §3)

Esecuzione: [EsempioOmonimie](src/main/java/it/universita/esse3/omonimie/EsempioOmonimie.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | `esami/Esame`, `appelli/Esame` | stesso nome semplice, tipi diversi in package diversi |
| ✅ | `carriere/Stato`, `appelli/Stato`, `tasse/Stato` | tre `Stato` con significati diversi |
| ✅ | `appelli/Prenotazione`, `infrastrutture/Prenotazione` | due `Prenotazione` con significati diversi |
| ✅ | [ReportEsami](src/main/java/it/universita/esse3/omonimie/report/ReportEsami.java) | un import esplicito più il nome completamente qualificato per l'altro tipo |
| ❌ | [DueImportStessoNome](src/test/resources/non-compilabili/DueImportStessoNome.java.txt) | due import con lo stesso nome semplice: errore di compilazione |
| ❌ | [MetodoInesistente](src/test/resources/non-compilabili/MetodoInesistente.java.txt) | metodo di un `Esame` usato sull'altro: errore di compilazione |
| ❌ | [NomeFileDiverso](src/test/resources/non-compilabili/NomeFileDiverso.java.txt) | classe `public` in un file con nome diverso: errore di compilazione |

### `visibilita/` - modificatori di accesso (dispensa §4)

Esecuzione: [EsempioVisibilita](src/main/java/it/universita/esse3/visibilita/EsempioVisibilita.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | [Appello](src/main/java/it/universita/esse3/visibilita/Appello.java) | campi `private`, API pubblica che controlla le precondizioni |
| ✅ | [Studente](src/main/java/it/universita/esse3/visibilita/Studente.java) | stato sempre valido (crediti non negativi) |
| ✅ | [CalcoloPostiDisponibili](src/main/java/it/universita/esse3/visibilita/CalcoloPostiDisponibili.java) | classe package-private |
| ❌ | [AppelloScadente](src/main/java/it/universita/esse3/visibilita/AppelloScadente.java) | campi pubblici e nessun controllo: posti negativi |
| ❌ | [AccessoCampoPrivato](src/test/resources/non-compilabili/AccessoCampoPrivato.java.txt) | accesso a un campo `private` da fuori: errore di compilazione |
| ❌ | [AccessoClassePackagePrivate](src/test/resources/non-compilabili/AccessoClassePackagePrivate.java.txt) | classe package-private usata da un altro package: errore di compilazione |

### `blocchi/` - blocchi e indentazione (dispensa §5)

Esecuzione: [EsempioBlocchi](src/main/java/it/universita/esse3/blocchi/EsempioBlocchi.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | [ControlloSoglia](src/main/java/it/universita/esse3/blocchi/ControlloSoglia.java) | `if` con le parentesi graffe |
| ❌ | [ControlloSogliaSenzaGraffe](src/main/java/it/universita/esse3/blocchi/ControlloSogliaSenzaGraffe.java) | senza graffe, la seconda istruzione è eseguita sempre |

## Controesempi che non compilano

I file in [src/test/resources/non-compilabili](src/test/resources/non-compilabili) hanno estensione `.java.txt`, così non rompono la build.
[CompilatoreDiControesempi](src/test/java/it/universita/esse3/CompilatoreDiControesempi.java) li compila durante i test e verifica il codice d'errore del compilatore.

## Test

| Test | Argomento |
|---|---|
| [ConvenzioniSuiNomiTest](src/test/java/it/universita/esse3/ConvenzioniSuiNomiTest.java) | `nomi` |
| [PackageEImportTest](src/test/java/it/universita/esse3/PackageEImportTest.java) | `omonimie` |
| [VisibilitaTest](src/test/java/it/universita/esse3/VisibilitaTest.java) | `visibilita` |
| [BlocchiTest](src/test/java/it/universita/esse3/BlocchiTest.java) | `blocchi` |

## Comandi

```bash
mvn test       # dalla cartella esempi-java-base o da questa
mvn compile
java -cp target/classes it.universita.esse3.blocchi.EsempioBlocchi
```

In VS Code si può anche usare **Run** sopra il `main` di ciascun `Esempio*`.
