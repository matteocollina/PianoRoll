# PianoRoll
microtonal roll plane

TODO:
Piano roll microtonale: creare un’applicazione Swing che visualizzi un’interfaccia in stile piano roll in
cui però le note non corrispondono alla scala cromatica del sistema temperato equabile, ma possa
essere scelta frequenza iniziale, frequenza finale e numero di suddivisioni dell’asse verticale

A sinistra nella schermata principale una pseudo-tastiera (dove ovviamente non ci sono i tasti bianchi e neri ma solo una sequenza di tasti uguali) ed a destra di essa una griglia di posizioni corrispondenti al tempo (in orizzontale) e alla nota (in verticale). L’utente dovrebbe poter disegnare in qualche modo le note da suonare e poterle riprodurre. Le impostazioni potrebbero riguardare il timbro da utilizzare, la frequenza minima e massima, il numero di suddivisioni della tastiera e i BPM per l’esecuzione.


- il numero di misure dovrebbe essere impostabile dall’utente, magari anche solo in fase di setup e non più modificabile poi
- anche questo parametro dovrebbe essere impostabile, ma se preferisce semplificare un po’ può anche essere lasciato fisso a 4/4
- in questo caso si può decidere che la durata minima sia fissa a 1/16, 1/8 o 1/4, e l’utente si deve adattare