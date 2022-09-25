# APK <!-- omit in toc -->

gitlab-ci construit et publie 2 APK : 

* https://univlille.gitlab.io/startupweek/2021/squelette/app-debug.apk
* https://univlille.gitlab.io/startupweek/2021/squelette/app-release-unsigned.apk

# Client Android <!-- omit in toc -->

***Ce repo contient le code de l'appli mobile Android de la startup week.***

***Il communique (comme le [client web](https://gitlab.com/univlille/startupweek/2021/squelette/client-web)), avec le serveur REST disponible dans le repo "[server](https://gitlab.com/univlille/startupweek/2021/squelette/server)" que je vous recommande de lancer avant d'attaquer ce README***

## Sommaire <!-- omit in toc -->
- [TL;DR](#tldr)
- [1. Réglages de l'application:](#1-réglages-de-lapplication)
- [2. Configurations spécifiques](#2-configurations-spécifiques)

## TL;DR
1. Fork + clone du repo
2. ouvrir le projet dans android studio
3. lancer le build
4. Lancer le terminal virtuel (AVD) et lancer l'exécution de l'application
5. Lancer le serveur REST
6. L appui sur le bouton devrait faire afficher `got it`dans la zone de texte.

## 1. Réglages de l'application:
* ```URL```: l'url complète de la ressource testée (.../api/v1/myresource).
   Réglée initialement sur l ip 10.0.2.2 à utiliser quand on fonctionne en emulation de terminal. vers la machine de développement.
* ```VOLLEY_TAG```: Marqueur utilisé pour supprimer toutes les requêtes en attente lors de la fermeture de l'application (onStop)

## 2. Configurations spécifiques
Cette application a été construite en partant du modèle 'Empty activity' et en ajoutant les réglages et primitives nécessaires:
* Bibliothèque ```VOLLEY``` dans ```graddle scripts/build.graddle (Module app)```
* Modification du Manifest (clauses ```uses-permission``` et ```usesCleartextTraffic```)
L'activité initiale fait référence à ```content_main.xml``` qui contient la description réelle de l'IHM

## Troubleshooting

### Le client android ne se connecte pas au serveur
Erreur: `com.android.volley.ServerError`

LogCat: 
```
E/Volley: [94854] BasicNetwork.performRequest: Unexpected response code 301 for 
 http://skeleton.azae.eu/api/v1/myresource 
```

**Correction**: utiliser le protocole https plutôt que http.

Si une connection via l appli androidne fonctionne pas, il est toujours possible de tester un acces par le navigateur embarqué du terminal. 
Si ça ne passe pas non plus avec le navigateur, le problème vient de l'infrastructure réseau.

### Comment se connecter à un serveur REST à domicile

On peut remplacer l'IP 10.0.2.2 dans le code du programme par l'IP du serveur dans le réseau local
 (par exemple 192.168.0.7) si vous êtes sur le wifi d une box (réviser vos cours de réseaux pour obtenir l IP 
 d'une machine).
 
 Attention: cette mise en place n est pas propice à la réalisation de demos. Donc privilégiez l'emulation de terminal dans android studio.
 
 *note*: La chaîne 10.0.2.2 a été déplacée dans les ressources sur la dernière version du squelette.
 (TODO: permettre le parametrage de cette valeur par l application)

Cela devrait résoudre le problème.
<br>Dans le cas contraire, faites part de votre problème dans le salon #help !
