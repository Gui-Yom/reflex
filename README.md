# reflex

Ce fichier décrit le contenu de l'archive ainsi que les instructions à suivre pour compiler et lancer le programme.

## Contenu

- `src` : Sources du programmes
- `resources` : Ressources du programme (image de fond)
- `build.bat` : script de compilation du programme (explicité plus bas)

## Prérequis

Il est nécessaire d'avoir installé sur sa machine un JDK de version >= 11. Il faut ajouter le chemin racine du JDK à la
variable d'environnement `JAVA_HOME` (`C:\Program Files\AdoptOpenJDK\jdk-11`) ou ajouter le
chemin `C:\Program Files\AdoptOpenJDK\jdk-11\bin` du JDK au `PATH`.

## Instructions (script de compilation, windows seulement)

Il suffit par la suite de lancer le script `build.bat` afin de compiler le programme. Il est possible de récupérer le
programme une fois compilé à cet emplacement : `build/reflex.jar`.

## Compilation manuelle

Comme expliqué ci-dessus, utiliser `build.bat` est la méthode la plus simple pour compiler le programme. Si jamais il
est impossible d'utiliser ce script, il faut utiliser les commandes suivantes :

```
javac -encoding UTF-8 -cp src -d build\classes --release 11 src\reflex\Main.java
jar --create -f build\reflex.jar -m src\META-INF\MANIFEST.MF -C build\classes . -C resources .
```

## Lancement

Pour le lancer il faut utiliser la commande `java -jar build/reflex.jar`. (ou double cliquer dessus)
