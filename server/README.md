#### Projet agile <!-- omit in toc -->
# Serveur REST <!-- omit in toc -->

***Ce repo contient le code de l'API REST du projet agile. Cette API est interrogée par le [client android](https://gitlab.com/univlille/startupweek/2021/squelette/client-android) et le [client web](https://gitlab.com/univlille/startupweek/2021/squelette/client-web).***

Il est recommandé de lancer ce serveur **AVANT** de démarrer le setup des deux clients.

## Sommaire <!-- omit in toc -->
- [TL;DR](#tldr)
- [Setup du projet](#setup-du-projet)
	- [1. Installation](#1-installation)
	- [2. Lancement du serveur pour les tests](#2-lancement-du-serveur-pour-les-tests)
	- [3. Génération de l'archive de déploiement](#3-génération-de-larchive-de-déploiement)
- [Manipulation de la base de données](#manipulation-de-la-base-de-données)
	- [Remise à zéro de la BDD](#remise-à-zéro-de-la-bdd)
- [Troubleshooting](#troubleshooting)
	- [J'ai une erreur de compilation avec maven](#jai-une-erreur-de-compilation-avec-maven)
	- [J'ai une erreur `Missing named parameter 'XXX' in binding` avec JDBI](#jai-une-erreur-missing-named-parameter-xxx-in-binding-avec-jdbi)
	- [J'ai un problème avec mon DAO](#jai-un-problème-avec-mon-dao)
	- [J'ai une erreur côté serveur mais pas de traces](#jai-une-erreur-côté-serveur-mais-pas-de-traces)
	- [Je n'ai pas d'affichage des DTO échangés](#je-nai-pas-daffichage-des-dto-échangés)
	- [Les tests qui impliquent une comparaison avec un DTO ne fonctionnent pas](#les-tests-qui-impliquent-une-comparaison-avec-un-dto-ne-fonctionnent-pas)

## TL;DR
1. Fork + clone du repo
2. Dans un terminal :
	```bash
	cd server
	mvn compile exec:java
	```
3. ouvrir dans le navigateur http://localhost:8080/api/v1/myresource

## Setup du projet
### 1. Installation
Faites un fork de ce repo et clonez dans le dossier de votre choix.

### 2. Lancement du serveur pour les tests
Le serveur se lance avec la commande `mvn exec:java`. La compilation
n'est pas automatique pour cette tâche donc vous devriez plutôt
utiliser : `mvn compile exec:java`.

<img alt="" width="480" src="https://gitlab.com/univlille/defi-agile-iut/skeleton/server/uploads/35bec6ee34eacb5fde539f7a06b26d8e/mvn-compile-exec-java.gif" >

La ressource exemple fournie est accessible à l'URL
http://localhost:8080/api/v1/myresource

Si tout fonctionne correctement, la  page doit afficher le texte "Got it!".

Pour arrêter le serveur, il faut faire un <kbd>CTRL</kbd> + <kbd>C</kbd>

### 3. Génération de l'archive de déploiement
On peut générer une archive avec l'ensemble du code et des librairies
nécessaires pour un déploiement à l'aide de la commande `mvn package`.

Vous obtiendrez dans le répertoire `target` une archive exécutable
nommée `server-1.0-SNAPSHOT.jar`.

Cette archive peut être exécutée au moyen de la commande `java -jar
target/server-1.0-SNAPSHOT.jar`.

## Manipulation de la base de données
La base de données à utiliser est [SQLite](https://www.sqlite.org), vous y accéderez dans vos DAO à travers [JDBI](jdbi.org).

### Remise à zéro de la BDD
Une fois votre application déployée sur `Deliverous`, vous n'aurez pas d'accès direct à votre base. Cela peut poser problème en cas de changement de schéma de vos tables.
Le squelette est fournit avec une ressource qui permet de faire la remise à zéro de la BDD (toutes les tables sont effacées). Cette ressource peut être utilisée de la façon suivante :

~~~
$ curl -i http://<serveur de déploiement>/api/v1/cleardatabase
~~~

Cette ressource pourra être retirée quand le développement sera finalisé (i.e., le schéma de la base n'évolue plus).

## Troubleshooting

### J'ai une erreur de compilation avec maven

Le squelette est prévu pour être compilé avec JAVA 11.

Vérifier que la variable JAVA_HOME pointe bien vers un jdk JAVA 11.
Cette variable désigne un seul répertoire (et pas une liste), donc pas de séparateur (: ou ; sur linux / windows)


Cela devrait résoudre le problème.
<br>Dans le cas contraire, faites part de votre problème dans le salon #help !

### J'ai une erreur `Missing named parameter 'XXX' in binding` avec JDBI
Les noms des paramètres ne sont pas conservés lors de la compilation Java et JDBI n'arrive pas à faire le lien avec les noms de variable dans le SQL.
Il faut faire le lien explicitement "à la main" avec l'annotation `@Bind` (voir https://jdbi.org/#_binding_arguments_2).

### J'ai un problème avec mon DAO
Un logger est fournit dans le code (`fr.ulille.iut.agile.JdbiSqlLogger`). Il peut être activé dans la classe `fr.ulille.iut.agile.BDDFactory` (le code est en commentaire) :

~~~java
 jdbi.setSqlLogger(new JdbiSqlLogger());
~~~

### J'ai une erreur côté serveur mais pas de traces
La classe `fr.ulille.iut.agile.DebugMapper` permet de capturer les exceptions du serveur et d'afficher la stack trace.

Elle doit être activée dans la classe `fr.ulille.iut.agile.Main` (le code est en commentaire) :

~~~java
rc.register(DebugMapper.class**;
~~~

**ATTENTION**, cette classe capture toutes les exceptions, il faut la désactiver quand vous n'en avez plus besoin !

### Je n'ai pas d'affichage des DTO échangés
La classe `fr.ulille.iut.agile.Main` configure une trace des requêtes et des réponses. Si vous voulez avoir un affichage du contenu des représentations transportées, il faut implémenter `toString()` dans vos DTO.

### Les tests qui impliquent une comparaison avec un DTO ne fonctionnent pas
Pensez à implémenter `equals()` (et `hashCode()` tant qu'à faire) sur vos différents JavaBeans.
