#### Projet agile <!-- omit in toc -->
# Client Web <!-- omit in toc -->

_**Ce repo contient le code du front web du projet agile.**_

_**Il communique (comme le front android), avec le serveur REST disponible dans le repo "[server](https://gitlab.com/univlille/startupweek/2021/squelette/server)" que je vous recommande de lancer avant d'attaquer ce README**_

## Sommaire <!-- omit in toc -->
- [TL;DR](#tldr)
- [A. Setup du projet](#a-setup-du-projet)
	- [1. Installation](#1-installation)
	- [2. Lancement du serveur web](#2-lancement-du-serveur-web)
	- [3. Compilation du JS](#3-compilation-du-js)
	- [4. Tests automatisés](#4-tests-automatisés)
- [B. FAQ](#b-faq)
- [C. Troubleshooting](#c-troubleshooting)

## TL;DR
1. Fork + clone du repo
2. Ouvrir le dossier `client-web` dans vscod[e/ium]
3. Dans un terminal :
	```bash
	npm i && npm start
	```
3. Dans 2 autres terminaux côte à côte :
	```bash
	npm run watch
	```
	et
	```
	npm test --watch
	```

## A. Setup du projet
### 1. Installation

1. Faites un fork de ce repo et clonez dans le dossier de votre choix.
2. Ouvrez le projet dans vscod[e/ium] puis ouvrez un terminal intégré (<kbd>CTRL</kbd>+<kbd>J</kbd>)
3. Puis installez les dépendances du projet avec la commande :
	```bash
	npm i
	```
	> _**NB :** `npm i` est un **raccourci** pour `npm install`_

	Le repo est en effet initialisé avec un fichier `package.json` prêt à l'emploi (_pour plus d'infos, voir FAQ : [Que contient le `package.json` fourni ?](#que-contient-le-packagejson-fourni-)_).

### 2. Lancement du serveur web

**Le client web dispose de son propre serveur HTTP. Son rôle est simple : servir tous les éléments statiques de l'application (les fichiers html, css, js, images, etc.).**

Ce serveur web doit donc être lancé **en parallèle** du serveur REST (_qui lui doit en principe être déjà lancé sur le port `8080`_) :
1. **Lancez notre serveur web statique** en tapant :
	```bash
	npm start
	```

	<img alt="" width="480" src="https://gitlab.com/univlille/defi-agile-iut/skeleton/client-web/uploads/7582c3ae30463f2888942e7e4bf300e4/npm-start.gif" />

	***NB :** vous aurez sans doute remarqué que la commande est différente de ce que vous aviez fait jusque là en TP de JS. Pour plus d'explications voir dans la FAQ : [Où est passée la commande `npx serve -s -l 8000` ? et c'est quoi ce `npm start` ?](#où-est-passée-la-commande-npx-serve-s-l-8000-et-cest-quoi-ce-npm-start-)*

2. **Vérifiez que la page `public/index.html` s'affiche bien dans votre navigateur** en ouvrant l'adresse http://localhost:8000/. Le résultat attendu est le suivant :

	<img alt="" src="https://gitlab.com/univlille/defi-agile-iut/skeleton/client-web/uploads/8960ccf488eeae4ec3196a2138f89fbe/localhost-sans-js.png" >

### 3. Compilation du JS

**Par défaut votre page HTML déclenche une erreur [404](https://http.cat/404) (_visible dans les devtools_) puisqu'elle s'attend à trouver un fichier `public/build/main.bundle.js` qui n'existe pas encore.**

1. **Dans un second terminal** (_je vous recommande un 2e terminal splitté dans VSCodium_), **lancez la compilation en mode "watch"** :
	```bash
	npm run watch
	```

	<img alt="" src="https://gitlab.com/univlille/defi-agile-iut/skeleton/client-web/uploads/efb0c488306150dcced82dbe4ac8b759/npm-run-watch.gif" width="480" >

2. **Vérifiez que la page `public/index.html` affiche maintenant le texte retourné par le serveur REST** : rechargez http://localhost:8000/. Le résultat attendu est le suivant :

	<img alt="" src="https://gitlab.com/univlille/defi-agile-iut/skeleton/client-web/uploads/f747f3a7ba2d4f3c01dd941b0041aaa1/localhost-avec-js.gif" >

### 4. Tests automatisés

**Ce squelette vous est fourni avec un système de tests unitaires automatisés.**

Il contient notamment :
- un **exemple** de test (`src/utils/url.test.js`) qui teste la fonction `getApiUrl()` décrite dans `src/utils/url.js`
- et une **commande `npm test`** qui permet de lancer les tests du projet grâce à [Jest](https://jestjs.io/) qui est l'un des outils les plus populaires dans l'écosystème JS pour les tests (_et qui figure donc dans les dépendances listées dans le `package.json`_)

1. **Si vous lancez la commande `npm test` dans le dossier du projet vous verrez ceci :**

	<img src="https://gitlab.com/univlille/startupweek/2021/squelette/client-web/uploads/58b253b15b73ad8112e837cb8d5f7886/npm-test.gif" />

	Cette commande scanne votre projet à la recherche de fichiers `*.test.js` puis les lance un à un.

2. **Ce que je vous recommande c'est de lancer cette commande mais avec l'option `--watch`** ce qui permet de lancer la commande en boucle dès qu'un fichier est modifié.

	**Lancez la commande suivante dans un terminal splitté à côté du `npm run watch`** (l'idéal est d'avoir toujours sous la main l'output de ces 2 commandes) :

	```
	npm test -- --watch
	```

	<img src="https://gitlab.com/univlille/startupweek/2021/squelette/client-web/uploads/4fdb407839b5ff17bede366210f04a8a/npm-test-watch.gif">

	Comme vous le voyez la commande ne lance pour le moment aucun test et se met en attente.

	En effet, jest ne va par défaut lancer **que les tests qui concernent des fichiers qui ont été modifiés depuis le dernier commit** !

	Modifiez par exemple le code de la fonction `getApiUrl()` et constatez que les tests se lancent alors automatiquement :

	<img src="https://gitlab.com/univlille/startupweek/2021/squelette/client-web/uploads/1605d443ae3e7ec0d031160533b47dc3/npm-test-watch-fail.gif" />

**Voilà, vous êtes maintenant prêt à coder !**

## B. FAQ

### Que contient le `package.json` fourni ? <!-- omit in toc -->
Il contient principalement la liste des dépendances du projet. Il s'agit notamment des outils qui vont servir à la compilation du code JS moderne pour le rendre compatible avec les anciens navigateurs, à savoir :
- [babel](https://babeljs.io/) pour la compilation des syntaxes ES6+ en ES5
- [webpack](https://webpack.js.org/) pour la gestion des modules ES6 (import/export)

Il contient également différents scripts qui permettront de lancer la compilation et le serveur HTTP statique.

### Où est passée la commande `npx serve -s -l 8000` ? et c'est quoi ce `npm start` ? <!-- omit in toc -->
Pour mémoire la commande que l'on utilisait dans les TPs pour lancer le serveur web était : `npx serve -l 8000`.

En réalité si vous inspectez le fichier `package.json` vous verrez que l'on retrouve bien cette commande dans la clé `"scripts"` :
```json
"start": "serve public -s -l tcp://0.0.0.0:8000"
```
Les seules différences avec ce qui était fait en TP sont :
- on n'utilise plus `npx`. A la place, le paquet [serve](https://www.npmjs.com/package/serve) est installé directement dans le projet (_cf. la clé `"dependencies"` du `package.json`_). [serve](https://www.npmjs.com/package/serve) n'est donc pas re-téléchargé à chaque fois (comme c'était le cas avec `npx`), ce qui permet d'accélérer sensiblement le lancement du serveur)
- on passe un paramètre supplémentaire à `serve` qui est le dossier dans lequel on veut lancer le serveur : il s'agit du dossier `public` (c'est celui qui contient la page HTML et tous les assets)
- pour écouter les connexions entrantes sur tous les noms de domaines (et pas que "localhost"), on passe `tcp://0.0.0.0:8000` et plus uniquement le numéro de port.

### Peut-on utiliser une lib JS comme React, Vue, etc. ? <!-- omit in toc -->
Ce n'est pas interdit mais pas spécialement conseillé non plus : ne perdez pas de vue que n'importe qui dans l'équipe doit être capable d'intervenir sur toute la stack du projet, il faut donc que tout le monde soit opérationnel et à l'aise avec la/les librairies que vous utiliserez, cela peut être à double tranchant.

### Ma question n'est pas là ? <!-- omit in toc -->
Envoyez votre question sur discord dans le channel `#help` !

## C. Troubleshooting

### la commande `npm install` reste bloquée ou retourne un timeout <!-- omit in toc -->
Inspectez votre configuration npm pour vérifier que vous avez correctement configuré le proxy : si vous êtes sur le réseau de l'IUT il faut configurer un proxy, si vous passez par un autre réseau il n'en faut pas :
```
npm config get proxy
```

1. Si vous êtes **en dehors** du réseau de l'IUT (par exemple chez vous) et qu'un proxy avait été configuré, supprimez le à l'aide de la commande :
	```bash
	npm config delete proxy
	```
2. Si vous êtes **derrière un proxy (salles de TP)** et que la commande `npm config get proxy` retourne `null`, alors ajoutez le proxy avec :
	```bash
	npm config set proxy http://193.49.225.25:3128/
	```

Cela devrait résoudre le problème. \
Dans le cas contraire, faites part de votre problème dans le salon #help !