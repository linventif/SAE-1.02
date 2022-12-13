<!-- a envoyer a tekwa.tedjini@univ-lille.fr -->

# Informations Générales

Nom du projet : Jeu du Désamorceur

Auteur : [Linventif](https://github.com/linventif)

Date de création : 2022-12-12

# Description

Le jeu du Désamorceur consite a désamorcer une bombe en résolvant plusieur problèmes, ils peuvent t'être d'ordre logique, mathématique, linguistique, etc.
Pour ce faire, il dispose d'un manuel d'utilisation de la bombe, qui lui donne des etapes et les règles de résolution de chaque module de la bombe.
Certaines parties du manuel sont cependant manquantes, et le joueur devra
Pas de gestion du temps prévue pour la version textuelle.

# Traces d'exécution textuelle

Au lance du jeu, une menu principale souvre et celui ci permet de :

- Lancer une partie

- Tableau des Scores

- Paramètres

- Quitter

## Lancer une partie

Au lancement d'une partie, après cela il a un résumé bref de sa mission lui expliquant le context et ce qu'il doit faire.

```text
Bonjour Désamorceur, vous avez été choisi pour désamorcer une bombe.
Voici votre manuel d'utilisation de la bombe, il vous expliquera comment la désamorcer.
Cependant la bombe est instable, si vous faite 3 erreurs, la bombe explosera.
Bon courage.
```

Pour que l'expliquation se ferme et que la partie commence le joueur doit appuyer sur entrée.

Après cela le joueur peut voir la bombe, et le manuel.

L'écran du joueur est divisé en 3 parties :

```text
|                 |                 |
|      Bombe      |      Manuel     |
| _ _ _ _ _ _ _ _ | _ _ _ _ _ _ _ _ |
|        Liste des Commandes        |
```

### Bombe

La bombe et ces module sont représenté par des caractères ASCII.

Les intéractiosn avec la bombe et le manuel se font par des commandes.

### Commandes

#### Bombe

- tourner [direction] : tourne la bombe dans la direction [direction] (haut, bas, gauche, droite)

- module [id] : affiche le module [id] et les commandes associées

#### Manuel

- page suivante : affiche la page suivante du manuel

- page précédente : affiche la page précédente du manuel

- page [id] : ouvre la page [id] du manuel

## Tableau des Scores

Le tableau des scores est un tableau qui affiche les meilleurs scores.

Le tableau des scores est composé de 3 colonnes de 10 lignes :

- Nom

- Score

- Date

### Calcul du score

Le score est calculé en fonction du nombre d'erreur commise, de la difficulter et du temps mis pour résoudre la bombe.

Equation : `((Nombre de Module Fait * 1000) + (1000 * difficulté)) / (1 + (temps / temps max))`

## Paramètres

Le menu des paramètres permet de :

- Changer la difficulté

### Difficulté

- Facile : `1`

- Moyen : `2`

- Difficile : `3`

La difficulté est un nombre compris entre 1 et 3, plus la difficulté est élevé, plus la bombe est complexe et le score est élevé.

# Types

## Manuel

- Page du manuel : `Page`

## Bombe

- Bombe : `Bombe`

- Face : `Face`

- Module : `Module`