# PianoRoll Microtonale

![alt text](https://www.pinterest.com/pin/299982025177563650/)

Applicazione Swing che visualizza un’interfaccia in stile piano roll in
cui però le note non corrispondono alla scala cromatica del sistema temperato equabile, ma possa essere scelta frequenza iniziale, frequenza finale e numero di suddivisioni dell’asse verticale.

A sinistra nella schermata principale una pseudo-tastiera (dove non ci sono i tasti bianchi e neri ma solo una sequenza di tasti uguali) ed a destra di essa una griglia di posizioni corrispondenti al tempo (in orizzontale) e alla nota (in verticale). 

L’utente può disegnare le note da suonare e riprodurle. 

Le impostazioni riguardano il timbro da utilizzare, la frequenza minima e massima, il numero di suddivisioni della tastiera e i BPM per l’esecuzione ed il numero di misure.

La durata minima delle note è 1/16 ma è possibile cambiarlo nel ConfigManager con effettiva rimodellazione del PianoRoll.


## Implementazioni future ##  
- Possibilità di inserire note di durata diversa  
- Effetto grafico al play di una nota  
- Inserimento bottone "loop" che cicla la sequenza  
- Ottimizzazione salvataggio settings con mantenimento dello score  
