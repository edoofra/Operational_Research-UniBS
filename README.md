# GUROBI PROJECT GRUPPO 7

## First part

Operational research project focused on the use of **Gurobi resolver** for solving a linear programming problem.
The Gurobi Optimizer enables users to state their toughest business problems as mathematical models and then finds the best solution out of trillions of possibilities. Gurobi Optimizer can also become a decision-making assistant, guiding the choices of a skilled expert or even run in fully autonomous mode without human intervention. Read the Gurobi Product Brochure to find out more.

The first part of the project consists of the solution to these 3 questions:

### Quesiti parte 1

1. Implementare il modello fornito tramite Gurobi e trovarne la soluzione ottima (valore ottimo delle variabili e corrispettivo valore della funzione obiettivo). Indicare le variabili in base e quelle fuori base ed i relativi coefficienti di costo ridotto all’ottimo. Dire se la soluzione ottima trovata `e multipla e/o degenere, spiegando brevemente il ragionamento fatto.

2. Trovare la soluzione ottima del problema D, duale di P. Trovare la possibile variazione che il termine noto del settimo vincolo del duale pu`o avere affinch ́e la base ottima duale rimanga quella trovata.

3. Trovare, se esiste, un’ulteriore soluzione ottima di base per il problema P. In caso non esista, trovare la solu- zione di base duale associata alla soluzione di base primale con variabili in base xB = (x1, x2, x3, x4, x5, x6, x7, x8) e dire se essa `e ammissibile per D.

***

## Part 2

The second part of the project consists of the solution to these 3 questions about PLI and Graphs:

### Quesiti parte 2

1. I  Dopo aver letto l’istanza di MMKP fornita e creato il modello associato, implementare una delle tecniche di preprocessing viste a lezione, sapendo che si conosce, per l’istanza fornita, una soluzione ammissibile con valore di funzione obiettivo pari a 25663. Riportare nella descrizione sintetica i passaggi effettuati. Dopo aver applicato il metodo di preprocessing implementato, risolvere l’istanza, imponendo a Gurobi un tempo limite pari a 120 secondi. Riportare il valore della funzione obiettivo della soluzione ottenuta (non necessariamente ottima).
2. II  Una volta letta l’istanza di TSP assegnata, creare e risolvere il rilassamento rappresentato dal problema di assegnamento (modello del TSP senza i vincoli di subtour elimination, di connectivity o di Miller-Tucker-Zemlin (MTZ)). Riportare il valore della funzione obiettivo della soluzione ottima per il problema di assegnamento. Implementare poi un metodo che individui almeno un sottociclo (se esiste) nella soluzione ottenuta. Riportare il sottociclo individuato.
3. III  Includere nel modello i vincoli di MTZ e risolvere all’ottimo il problema con Gurobi. Riportare il valore della funzione obiettivo della soluzione individuata, il tempo computazionale e il ciclo ottimo corrispondente a tale soluzione.

