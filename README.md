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

Au lance du jeu, le joueur a un résumé bref de sa mission lui expliquant le context et ce qu'il doit faire.

```text
Bonjour Agent Fédéral Owlo, vos services sont requis pour désamorcer une bombe.
Voici votre manuel d'utilisation de la bombe, il vous expliquera comment la désamorcer.
```

Pour que l'expliquation se ferme et que la parte commence le joueur doit entrer : desamorcer.

Après cela le joueur peut voir la bombe, et le manuel.

L'écran du joueur est divisé en 3 parties :

```text
|                 |                 |
|      Bombe      |      Manuel     |
| _ _ _ _ _ _ _ _ | _ _ _ _ _ _ _ _ |
|        Liste des Commandes        |
```

## Bombe

La bombe et ces module sont représenté par des caractères ASCII.

Les intéractiosn avec la bombe et le manuel se font par des commandes.

## Commandes

### Bombe

- tourner [direction] : tourne la bombe dans la direction [direction] (haut, bas, gauche, droite)

- module [id] : affiche le module [id] et les commandes associées

### Manuel

- page suivante : affiche la page suivante du manuel

- page précédente : affiche la page précédente du manuel

- page [id] : ouvre la page [id] du manuel

# Types

## Manuel

- Page du manuel : `Page`

## Bombe

- Bombe : `Bombe`

- Face : `Face`

- Module : `Module`