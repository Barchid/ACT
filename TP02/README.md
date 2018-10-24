# ACT : TP n°02 - Sami BARCHID  & Anthony SLIMANI

Ce travail pratique représente la solution apportée au problème "Hexapawn".

## Temps d'exécution
- Test 1 Succès (0.104s) 
- Test 2 Succès (0.1s) 
- Test 3 Succès (0.136s) 
- Test 4 Succès (0.18s) 
- Test 5 Succès (0.176s) 
- Test 6 Succès (2.176s) 
- Test 7 Succès (4.164s) 
- Test 8 Succès (0.124s)

## Formulation du problème

``Donnez une formule mathematique permettant de calculer la valeur d'une coniguration à partir des valeurs de ses successeurs.``

### Formule dans le cas où tous les successeurs ont une valeur positive :
- Soit ``E``, l'ensemble des configurations successeurs possibles.
``configuration = - ( Max(E) + 1)``

### Formule dans le cas où au moins un des successeurs est négatif
- Soit ``E``, l'ensemble des configurations successeurs possibles.
``configuration = |MaxDesNegatifs(E)| + 1``

## Contenu des sources

### package "questions"
Package principale de la solution. Contient l'algorithme et la classe main qui a servie aux tests.

### package "test"
Package contenant un fichier, Test.java, utilisé pour faire fonctionner les tests sur la plate-forme.