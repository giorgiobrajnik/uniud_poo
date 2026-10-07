# Identità, uguaglianza, `equals`/`hashCode` e copia di oggetti

Esempi (✅) e controesempi (❌) su: identità di riferimenti, uguaglianza per valore, contratto di `equals`, contratto con `hashCode`, `record`, confronti di stringhe/wrapper/array, aliasing, shallow/deep copy, copie difensive, copia per serializzazione.
Ogni cartella sotto `src/main/java/it/universita/esse3/` è un argomento e ha un `main` eseguibile (`Esempio*`). I test in `src/test/java` verificano ogni affermazione.

Esecuzione dei test: `mvn -pl 04_uguaglianza_copia test` dalla cartella `esempi-java-base`.

## Indice

### `identita/` - «è proprio lo stesso oggetto?» (§47.1)

Esecuzione: [EsempioIdentita](src/main/java/it/universita/esse3/identita/EsempioIdentita.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | [Studente](src/main/java/it/universita/esse3/identita/Studente.java) | `s1 == s2` per alias, `s1 == s3` falso dopo una seconda `new` |
| ❌ | [Studente](src/main/java/it/universita/esse3/identita/Studente.java) | senza `equals`, `s1.equals(s3)` usa l'identità e vale `false` |
| ✅ | [RepositoryStudenti](src/main/java/it/universita/esse3/identita/RepositoryStudenti.java) | stessa entità di dominio (matricola) in due istanze diverse |

### `uguaglianza/` - uguaglianza per valore (§47.2-47.4, 47.6)

Esecuzione: [EsempioUguaglianza](src/main/java/it/universita/esse3/uguaglianza/EsempioUguaglianza.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | [AnnoAccademico](src/main/java/it/universita/esse3/uguaglianza/AnnoAccademico.java) | `final class`, `equals` e `hashCode` sul solo dato rilevante |
| ❌ | [CodiceCorsoSenzaEquals](src/main/java/it/universita/esse3/uguaglianza/CodiceCorsoSenzaEquals.java) | `equals` non ridefinito: la JVM non indovina la semantica |
| ✅ | [CodiceCorso](src/main/java/it/universita/esse3/uguaglianza/CodiceCorso.java) | `equals(Object)` con `this == other`, `instanceof`, cast; `null` gestito |
| ✅ | [CodiceCorsoConPattern](src/main/java/it/universita/esse3/uguaglianza/CodiceCorsoConPattern.java) | `instanceof` con pattern variable (Java 16+) |
| ❌ | [CodiceCorsoOverload](src/main/java/it/universita/esse3/uguaglianza/CodiceCorsoOverload.java) | `equals(CodiceCorsoOverload)` è un overload: `List` e `HashSet` non lo usano |

### `contratto/` - proprietà di `equals` (§47.5)

Esecuzione: [EsempioContratto](src/main/java/it/universita/esse3/contratto/EsempioContratto.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | [EsempioContratto](src/main/java/it/universita/esse3/contratto/EsempioContratto.java) | riflessiva, simmetrica, transitiva, `equals(null)` falso su `CodiceCorso` |
| ❌ | [Persona](src/main/java/it/universita/esse3/contratto/Persona.java), [StudentePersona](src/main/java/it/universita/esse3/contratto/StudentePersona.java) | sottoclasse che aggiunge un dato: `p.equals(s)` vero, `s.equals(p)` falso |
| ❌ | [Misura](src/main/java/it/universita/esse3/contratto/Misura.java) | uguaglianza con tolleranza: non transitiva |
| ❌ | [EqualsIncoerente](src/main/java/it/universita/esse3/contratto/EqualsIncoerente.java) | risultato diverso a parità di stato: non consistente |

### `hashing/` - `equals` e `hashCode` (§47.6-47.10)

Esecuzione: [EsempioHashing](src/main/java/it/universita/esse3/hashing/EsempioHashing.java)

| | File | Cosa mostra |
|---|---|---|
| ❌ | [AppelloIdSenzaHash](src/main/java/it/universita/esse3/hashing/AppelloIdSenzaHash.java) | `equals` senza `hashCode`: `HashSet.contains` non lo ritrova |
| ✅ | [AppelloId](src/main/java/it/universita/esse3/hashing/AppelloId.java) | `equals` e `hashCode` sugli stessi campi (`Objects.hash`) |
| ✅ | [EsempioHashing](src/main/java/it/universita/esse3/hashing/EsempioHashing.java) | collisione lecita: `"Aa"` e `"BB"` hanno lo stesso hash ma non sono uguali |
| ❌ | [StudenteEmail](src/main/java/it/universita/esse3/hashing/StudenteEmail.java) | campo mutabile in `hashCode`: dopo `setEmail` l'elemento non si ritrova |
| ❌ | [StudenteTuttiICampi](src/main/java/it/universita/esse3/hashing/StudenteTuttiICampi.java) | «tutti i campi» non definiscono l'identità e cambiano nel tempo |
| ✅ | [StudenteMatricola](src/main/java/it/universita/esse3/hashing/StudenteMatricola.java) | entità: `equals`/`hashCode` sulla sola matricola stabile |

### `valori/` - value object e `record` (§47.5.1, 47.13)

Esecuzione: [EsempioValori](src/main/java/it/universita/esse3/valori/EsempioValori.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | [ImportoClassico](src/main/java/it/universita/esse3/valori/ImportoClassico.java) | value object scritto a mano: codice quasi tutto meccanico |
| ✅ | [Importo](src/main/java/it/universita/esse3/valori/Importo.java) | stessa semantica come `record`, con validazione nel costruttore compatto |
| ❌ | [PianoStudiSuperficiale](src/main/java/it/universita/esse3/valori/PianoStudiSuperficiale.java) | immutabilità superficiale: la lista del chiamante resta modificabile |
| ✅ | [PianoStudi](src/main/java/it/universita/esse3/valori/PianoStudi.java) | `List.copyOf` nel costruttore compatto |
| ❌ | [StudenteRecord](src/main/java/it/universita/esse3/valori/StudenteRecord.java) | un'entità come record: `equals` confronta anche il nome |
| ✅ | [StudenteRecordPerMatricola](src/main/java/it/universita/esse3/valori/StudenteRecordPerMatricola.java) | `equals`/`hashCode` espliciti, ma il vantaggio del record si riduce |

### `confronti/` - stringhe, wrapper, `Objects.equals`, array (§47.11-47.12, «String, wrapper»)

Esecuzione: [EsempioConfronti](src/main/java/it/universita/esse3/confronti/EsempioConfronti.java)

| | File | Cosa mostra |
|---|---|---|
| ❌ | [ConfrontoStringhe](src/main/java/it/universita/esse3/confronti/ConfrontoStringhe.java) | `new String("POO") == new String("POO")` è falso; con i letterali è vero solo per la condivisione della JVM |
| ❌ | [ConfrontoWrapper](src/main/java/it/universita/esse3/confronti/ConfrontoWrapper.java) | `Integer` 100 vs 1000 con `==`; `Integer.equals(Long)` è falso |
| ❌ | [PrenotazioneConNote](src/main/java/it/universita/esse3/confronti/PrenotazioneConNote.java) | un campo amministrativo non dovrebbe contare in `equals` |
| ✅ | [Prenotazione](src/main/java/it/universita/esse3/confronti/Prenotazione.java) | `Objects.equals` su campi opzionali, note escluse |
| ❌ | [VotiSbagliato](src/main/java/it/universita/esse3/confronti/VotiSbagliato.java) | `equals` su un campo array confronta i riferimenti |
| ✅ | [Voti](src/main/java/it/universita/esse3/confronti/Voti.java) | `Arrays.equals` e `Arrays.hashCode` |

### `copia/` - aliasing, shallow e deep copy (§48.1-48.11, 48.15, 48.17)

Esecuzione: [EsempioCopia](src/main/java/it/universita/esse3/copia/EsempioCopia.java)

| | File | Cosa mostra |
|---|---|---|
| ❌ | [Carriera](src/main/java/it/universita/esse3/copia/Carriera.java) | `c2 = c1` non copia; `copiaSuperficiale` condivide la lista |
| ❌ | [Carriera](src/main/java/it/universita/esse3/copia/Carriera.java) | `copiaConNuovaLista`: lista nuova, ma gli `EsameSuperato` mutabili sono condivisi |
| ✅ | [Carriera](src/main/java/it/universita/esse3/copia/Carriera.java) | `copiaProfonda`: nuova lista e nuovi esami |
| ❌ | [CopieDiCollezioni](src/main/java/it/universita/esse3/copia/CopieDiCollezioni.java) | `new ArrayList<>(a)`, `List.copyOf`, `clone()` di array di riferimenti non sono deep copy |
| ✅ | [CopieDiCollezioni](src/main/java/it/universita/esse3/copia/CopieDiCollezioni.java) | `unmodifiableList` è una vista, `copyOf` una copia non modificabile; `clone()` di `int[]` |
| ✅ | [PianoDiStudi](src/main/java/it/universita/esse3/copia/PianoDiStudi.java) | costruttore di copia con contratto documentato; factory `snapshotOf`; copia non uguale all'originale senza `equals` |
| ❌ | [PrenotazioneAppello](src/main/java/it/universita/esse3/copia/PrenotazioneAppello.java) | deep copy cieca: duplica un `AppelloEsame` che doveva restare condiviso |
| ✅ | [PrenotazioneAppello](src/main/java/it/universita/esse3/copia/PrenotazioneAppello.java) | `copiaCondivisa`: l'entità resta la stessa |
| ❌ | [CopiaGrafo](src/main/java/it/universita/esse3/copia/CopiaGrafo.java) | copia ricorsiva ingenua: duplica i nodi condivisi, `StackOverflowError` sui cicli |
| ✅ | [CopiaGrafo](src/main/java/it/universita/esse3/copia/CopiaGrafo.java) | `IdentityHashMap` originale → copia: preserva condivisioni e cicli |

### `difensiva/` - copie difensive e incapsulamento (§48.12-48.14, 48.18-48.20)

Esecuzione: [EsempioDifensiva](src/main/java/it/universita/esse3/difensiva/EsempioDifensiva.java)

| | File | Cosa mostra |
|---|---|---|
| ❌ | [AppelloEsposto](src/main/java/it/universita/esse3/difensiva/AppelloEsposto.java) | conserva la lista in ingresso; il getter espone la lista e aggira `prenota` e la chiusura |
| ✅ | [AppelloProtetto](src/main/java/it/universita/esse3/difensiva/AppelloProtetto.java) | copia in ingresso (`List.copyOf`) e in uscita |
| ✅ | [LibrettoImmutabile](src/main/java/it/universita/esse3/difensiva/LibrettoImmutabile.java) | con elementi immutabili basta copiare la lista |
| ❌ | [LibrettoElementiMutabili](src/main/java/it/universita/esse3/difensiva/LibrettoElementiMutabili.java) | `List.copyOf` non protegge gli elementi mutabili |
| ✅ | [LibrettoConSnapshot](src/main/java/it/universita/esse3/difensiva/LibrettoConSnapshot.java) | il client osserva snapshot immutabili (`record`) |

### `serializzazione/` - deep copy tramite serializzazione (§48.16)

Esecuzione: [EsempioSerializzazione](src/main/java/it/universita/esse3/serializzazione/EsempioSerializzazione.java)

| | File | Cosa mostra |
|---|---|---|
| ✅ | [CopiaPerSerializzazione](src/main/java/it/universita/esse3/serializzazione/CopiaPerSerializzazione.java) | oggetto → byte → nuovo oggetto indipendente |
| ❌ | [ReportSerializzabile](src/main/java/it/universita/esse3/serializzazione/ReportSerializzabile.java) | un campo `transient` non viene copiato |
| ❌ | [ReportConRisorsa](src/main/java/it/universita/esse3/serializzazione/ReportConRisorsa.java) | un campo non serializzabile fa fallire la copia (`NotSerializableException`) |
