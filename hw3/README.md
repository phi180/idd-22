# Homework 3


Suddivisione in moduli del progetto
- **api-3**: contiene le interfacce per i vari moduli
- **common-3**: contiene pezzi di codice condiviso tra i vari moduli
- **parse**: sfrutta le potenzialità di gson per deserializzare il json di una tabella
- **search-join**: contiene il necessario per l'esecuzione del search table join
- **stats**: per ottenere statistiche su un dataset

In ciascun modulo è possibile trovare delle classi di test per verificare correttezza dell'algoritmo

## Search-join

Possibilità di eseguire un'istanza del programma

`java search-join/target/search-join-1.0-SNAPSHOT.jar <indirizzo dataset json>`

Il programma costruisce l'indice del dataset specificato nei parametri ed esegue una query di prova dopo aver estratto una colonna a caso dal dataset.
Al termine dell'esecuzione vengono stampati i risultati a schermo e in target/results.
