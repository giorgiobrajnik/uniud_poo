# Intranet universitaria

Questo esempio modella un piccolo sistema universitario in memoria senza gerarchie, interfacce applicative o pattern. Gli oggetti collaborano per composizione e delega.

## Missioni e responsabilità

- `StudentId`, `CourseCode`, `ExamSessionId`, `CreditUnits` e `Grade` sono valori validati e immutabili.
- `Student` conserva l'anagrafica; l'uguaglianza dipende dalla matricola.
- `Prerequisite` esprime un corso e un voto minimo.
- `Course` possiede titolo, CFU e prerequisiti, e sa individuare quelli mancanti in una carriera.
- `ExamRecord` fotografa un esame verbalizzato.
- `Career` governa la funzione parziale corso → esito e calcola CFU e media pesata.
- `ExamSession` governa finestra, capienza, duplicati, iscrizioni e cancellazioni.
- `UniversityIntranet` possiede i registri e coordina le regole che attraversano più oggetti: prerequisiti, iscrizione e verbalizzazione.

Lombok genera soltanto uguaglianza, hash e descrizioni meccaniche dei value object. Le operazioni del dominio sono scritte esplicitamente.

## Contratti e invarianti principali

Ogni classe documenta missione, stato astratto, invariante di classe, AF e RI nel sorgente. Alcuni esempi:

- un `Grade` è tra 18 e 30 e la lode implica 30;
- in una `Career` esiste al più un esito per corso;
- in un `ExamSession`, `bookingOpens <= bookingCloses <= examDate` e gli iscritti non superano la capienza;
- nell'intranet ogni studente ha esattamente una carriera e corsi/appelli riferiscono entità registrate.

Le transizioni sono atomiche rispetto agli errori controllati: i prerequisiti vengono verificati prima di delegare `book`, e una verbalizzazione invalida non lascia un esito parziale.

## Errori e sentinelle

L'assenza attesa nelle interrogazioni usa `Optional`/`OptionalDouble`: `findStudent`, `findCourse`, `findSession`, `findCareer`, `resultFor`, `weightedAverage`. I comandi non validi usano eccezioni custom:

- `DuplicateEntityException`;
- `EntityNotFoundException`;
- `EnrollmentException`;
- `GradeRegistrationException`.

## Media pesata

La carriera calcola:

`somma(votoNumerico * CFU) / somma(CFU)`

Il 30 e lode contribuisce numericamente come 30. Una carriera vuota restituisce `OptionalDouble.empty()`.

## Esecuzione

```bash
mvn -f ../pom.xml -pl intranet_universitaria test
mvn -f ../pom.xml -pl intranet_universitaria exec:java \
  -Dexec.mainClass=it.uniud.tda.intranet.Main
```
