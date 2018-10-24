# ACT : TP n°02 - Sami BARCHID  & Anthony SLIMANI

Ce travail pratique représente la solution apportée au problème "Hexapawn".

## Temps d'exécution

## Formulation du problème

``Donnez une formule mathematique permettant de calculer la valeur d'une coniguration à partir des valeurs de ses successeurs.``

### Formule dans le cas où tous les successeurs ont une valeur positive :
- Soit ``E``, l'ensemble des configurations successeurs possibles.
``configuration = - ( Max(E) + 1)``

### Formule dans le cas où au moins un des successeurs est négatif
- Soit ``E``, l'ensemble des configurations successeurs possibles.
``configuration = |MaxDesNegatifs(E)| + 1``
