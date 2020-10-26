# MyERP
Projet 9 Openclassrooms

Réalisation de tests unitaires et de tests d'intégration automatisés en intégration continue

# Références :

- CONTACT : jeanlouispiera@yahoo.fr
- VERSION : 1.0
- Sept-Oct 2020

# Présentation Générale :

Ce projet est un projet académique dans le cadre du parcours développeur JAVA d'OPENCLASSROOMS.

- Application de comptabilité myerp : projet Spring Multi-Modules MAVEN 
- Mise en place de tests unitaires et d'intégration à l'aide de JUnit, Maven surefire, Maven Failsafe  et Jacoco
- Intégration Continue avec Jenkins à partir d'un repo privé GitHub

Ce projet a pour objectif l'implémentation de tests sur une application de comptabilité dont le code est fourni à l'exception de la couche IHM et la réalisation de tests automatisés en intégration continue.

Ces tests ont permis de corriger des bugs insérés volontairement dans le programme et d'obtenir un taux de couverture
minimum du code de 75%.

L'Appli à tester se présente sous 4 modules :

- le module DAO : myerp-consumer
- le module SERVICE : myerp-business
- le module ENtiTY : myerp-model
- le module EXCEPTION : myerp-technical
 

# Technologies : 
Ce projet a été écrit en Java avec JEE et la version 4.3.7.RELEASE de Spring. Toutes les méthodes sont commentées en Javadoc.

# Prérequis : 
L'environnement d'installation minimum est :
- IDE Eclipse 2020-06 version 4.16
- Apache Maven version m2 Eclipse Embedded
- JRE 1.8
- Database PostgreSQL 9.4.1212
- Docker version 2.0 minimum
- Jenkins vesion 4.0

# Installation de MyERP

- importer dans Eclipse le package à installer depuis le dépôt privé GitHub https://github.com/JeanLouisPIERA/comptaUnit


# Déploiement et démarrage de la base de données 
 
- ouvrir la ligne de commande sur la directory eclipse-workspace

- ouvrir l'interface graphique PostgreSQL PgAdmin et créer une base de données db_myerp à partir des données d'environnement indiquées dans le fichier eclipse-workspace\docker\dev\docker-compose.yml

- pour démarrer la base de données db_myerp sur PostgreSQL:
    - cd docker/dev
    - docker-compose up

- ouvrir à nouveau l'interface graphique PgAdmin pour vérifier que le jeu de données de démo a bien été installé


# Création d'un repo GitHub et paramétrage de votre Jenkins

- créer un repo GitHub pour le projet et réaliser le 1er commit 

- ouvrir Jenkins sur le port paramétré lors de son installation

- paramétrer le Jenkins avec les plugins nécessaires (PostgreSQL, Jacoco, publishHtML ...)

- créer un declarative pipeline à connecter au repo GitHub

Au prochain commit depuis Eclipse vers votre repo GitHub, les jobs Jenkins seront lancés automatiquement 1 minute après le commit


# Consulter les rapports de tests dans Jenkins

Le JenkinsFile installé à la racine de myerp a été paramétré pour permettre dans la consultation dans le remote Jenkins :

- du résultat de tous les tests à partir du dossier Résultat des Tests

- du résultat du rapport de couverture de chaque module (minimum du COVEREDRATE : 75%)


# Consulter les rapports de tests depuis le workspace-eclipse

Le lancement d'un build MAVEN dans l'IDE ECLIPSE génère pour chaque module les rapports de test et de couverture dans la directory
eclipse-workspace.

- consulter au format texte le rapport des tests unitaires dans eclipse-workspace\**\"nom-du -module"\target\surefire-reports.txt

- consulter au format texte le rapport des tests unitaires dans eclipse-workspace\**\"nom-du -module"\target\failsafe-reports.txt

- consulter au format HTML le rapport du rapport de couvertue dans 
eclipse-workspace\**\"nom-du -module"\target\site\jacoco-merged-test-coverage-report\index.html


# Remise à zéro et arrêt de la base de données 
 
Avant d'exit IDE Eclipse, il est recommandé de procéder à l'arrêt de la base de données dans Docker :

Pour démarrer la base de données db_myerp sur PostgreSQL:
    - cd docker/dev
    - docker-compose stop
    
Il peut être utile de remettre à zéro la base de données :
    - cd docker/dev
    - docker-compose stop
    - docker-compose rm -v
    - docker-compose up
	






