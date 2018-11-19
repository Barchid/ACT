## Réduction polynomiale

### Question 01 :
#### 1.1 : HamiltonCycle se réduit polynomialement dans TSP


### Question 02 :

### Question 03 :

### Question 04 :

### Question 05 :
S'il existe un algorithme polynomiale pour trouver la longueur minimale d'une tournée possible (TSPOpt1), alors il existe un algorithme polynomiale pour vérifier qu'il existe une tournée de longueur <= l (TSP). Il suffit d'appliquer l'algorithme TSPOpt1 et de vérifier que la longueur minimale obtenue est <= l.

S'il existe un algorithme polynomiale pour trouver la tournée possible de longueur minimale (TSPOpt2), alors il existe un algorithme polynomiale pour vérifier qu'il existe une tournée de longueur <= l (TSP). Il suffit d'appliquer l'algorithme TSPOpt2 et de vérifier que la longueur de la tournée minimale obtenue est <= l.

On en déduit donc que si TSPOpt1/TSPOpt2 était P, cela montrerait qu'il existe un algorithme polynomial pour un problème NP-dure et donc on aurait P = NP car la propriété NP-dure TSP contient toute la complexité de NP.

### Question 06 :