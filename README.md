# eternitypt
Projet tuteuré de Eternity II

# Générateur de puzzle aléatoires
## Compilation
Placez vous dans le dossier `eternitypt-main/eternitycplusplus/eternity/` et exécutez le code suivant :
```bash
make
```

## Execution
Placez vous dans le dossier `eternitypt-main/eternitycplusplus/eternity/` et exécutez le code suivant :
Sous windows : 
```bash
eternity [dimension] [nbMotifs]
```
Sous Linux : 
```bash
./eternity [dimension] [nbMotifs]
```

# Solveur RLS
## Compilation
Placez vous dans le dossier `eternitypt-main/SolveurRLS/` et exécutez le code suivant :
```bash
javac *.java
```


## Execution
Placez vous dans le dossier `eternitypt-main/SolveurRLS/` et exécutez le code suivant :
```bash
java Solveur
```
Il faut au moins 3 dimension et 2 motifs

# Solveur Choco
## Compilation 
Importez le projet eclipse via "file->import->project from folder or archive"

précisez l'adresse du fichier `export.txt` dans le fileName......................................

Vous pouvez maintenant compiler le projet avec le bouton run de Eclipse

Note importante : pour pouvoir lancer le programme pour la génération de puzzle, il faut installer la lib SFML pour l'interface graphique.
Vous pouvez le faire avec la commande suivante :
```bash
sudo apt install libsfml-dev
```
