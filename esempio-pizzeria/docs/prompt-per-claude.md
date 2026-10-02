
# Passo 1
> considera questa specifica: PizzaManager è un sistema di gestione per una pizzeria moderna che offre servizio al tavolo sia 
  all'interno che all'aperto. La pizzeria "Ai Rizzi" dispone di 25 tavoli distribuiti in due aree: 15 tavoli interni (da 2 a 8 
  coperti ciascuno) e 10 tavoli esterni (da 2 a 6 coperti ciascuno). I tavoli possono essere uniti per ospitare gruppi più 
  numerosi, creando configurazioni personalizzate.
  Il personale di sala è composto da 6 camerieri, ognuno responsabile di una specifica area di tavoli. Ogni cameriere ha un 
  codice identificativo, nome, cognome.
  Il menu è organizzato in categorie: pizze, antipasti, primi piatti, secondi di carne e pesce, contorni, dolci della casa e 
  bevande. Ogni pietanza ha un codice, nome, descrizione, prezzo, categoria, e disponibilità (alcune specialità sono disponibili
   solo in certi giorni).
  I clienti si siedono ai tavoli e comunicano gli ordini al cameriere di zona. Ogni ordine è caratterizzato da timestamp, numero
   tavolo (o gruppo di tavoli), cameriere responsabile, lista delle pietanze con quantità, stato dell'ordine (in preparazione, 
  servito, pagato), modalità di pagamento (contanti, carta, buoni pasto) e importo totale. Gli ordini possono avvenire in più 
  fasi durante il servizio - ad esempio prima le bevande e i primi, poi le portate principali. Durante una serata, allo stesso 
  tavolo si possono susseguire diversi gruppi di clienti. Il sistema deve tracciare separatamente ogni servizio, permettendo di 
  distinguere gli ordini di clienti diversi anche se utilizzano lo stesso tavolo fisico. Il sistema deve gestire in memoria 
  (senza persistenza o interfaccia utente) quanto serve per queste operazioni principali:
  Visualizzare gli ordini attivi in tempo reale, mostrando per ogni ordine non ancora pagato: tavolo/i coinvolti, cameriere 
  responsabile, lista dettagliata delle pietanze ordinate con quantità e prezzi, stato di preparazione, orario dell'ordine e 
  importo parziale.
  Calcolare il conto di un tavolo in un momento specifico, sommando tutte le pietanze ordinate e non ancora pagate, generando il
   totale da pagare con il dettaglio completo.
  Generare il resoconto serale con il totale degli incassi ripartito per cameriere, includendo numero di tavoli serviti, numero 
  totale di coperti, incasso lordo per cameriere, ripartizione degli incassi per categoria di menu (pizze, antipasti, primi, 
  etc.) e modalità di pagamento utilizzate.
  Implementare le classi Java necessarie con i relativi metodi, gestendo appropriatamente le relazioni tra tavoli, camerieri, 
  ordini e pietanze, e fornendo i metodi per le operazioni richieste.
  È obbligatorio produrre anche il codice del Main che illustri esempi di chiamata dei metodi che implementano queste 
  operazioni. 
  
  
spiegami che classi scriversti


----

# Passo 2
bene. Procedi tenedo conto di queste indicazioni: 
- scrivi anche unit test in junit, 
- segui il principi SOLID, e in particolare quello di singola
  responsabilità 
- segui il clean coding, 
- escludi tutte le problematiche relative alla UI e alla persistenza, 
- per ogni classe o interfaccia indica la sua mission (il ruolo che ha
  nel sistema) e la sua responsabilità (cosa sa, cosa sa fare), 
- per ogni classe  descrivi il suo stato astratto e concreto (come se
fosse un tipo di dato astratto), indica anche i possibili invarianti
di rappresentazione (che sono dei vincoli sullo stato concreto), 
- per ogni metodo  pubblico descrivi la sua specifica individuando le
pre e le post-condizioni. Le pre-condizioni sono dei predicati che ci
si aspetta siano veri alla chiamata del metodo, le post-condizioni
sono predictai che  sono veri al termine del metodo (assumendo che le
pre-c siano state vere prima). 

- Scrivi il codice in inglese, ma i commenti in italiano.
- usa design pattern come factory e observer, forse composite e decorator
- usa anche gli stream e la programmazione funzionale

# passo 3

sposta tutto dentro un folder chiamato 'compito-2025-09' che sta dentro uniud/esempi_poo-2/src/main/java/it/uniud/poo/compiti/ e fai in modo che i test funzionino
