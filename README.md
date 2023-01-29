# Gestor de documents

## Execució del programa i tests
Per executar el programa és necessari:   
1. Clonar el repositori en local   
```
git clone https://repo.fib.upc.es/grau-prop/subgrup-prop21.1.git
cd subgrup-prop21.1/
```

2. Compilar el codi:   
```
cd FONTS/
./compile.sh
```
Els fitxers resultats estaran a la carpeta EXE.   
Per executar-los haurem de navegar-hi i fer servir:   
```
java -jar [file.jar]
```
on file.jar pot ser:
- documentDriver.jar
- domainDriver.jar
- searchDriver.jar
- trieDriver.jar

3. Realitzar tests:

Per executar els tests cal estar a la carpeta FONTS i fer servir:   
```
./test.sh [classname]
```
on classsname és el nom de la classe que vols testejar (p.e.: Configuration, Document, Sentence, Trie...)   
Imporant: Per poder executar els testos es possible que sigui necessari tenir configurat correctament el classpath, per fer-ho:
```
export CLASSPATH=.:./lib/hamcrest-core-1.3.jar:./lib/junit-4.13.2.jar:./lib/junit-jupiter-api-5.9.1.jar:./lib/tika-app-2.6.0.jar:./lib/tika-app-2.6.0.jar
```
Per comprovar que esta configurat ens hauria de sortir els paths anteriors en fer:   
```
echo $CLASSPATH
```

Made by [AleexHrB](https://github.com/AleexHrB), [LlucCC](https://github.com/LlucCC), [eZWALT](https://github.com/eZWALT) and me.
